#!/bin/bash

# ==== Usage ====
# ./setup-vm.sh VM_NAME ROOT_PASS VM_RAM VM_VCPUS CLOUD_IMAGE_NAME \
#   VM_MAC VM_IP VM_GATEWAY CIDR VM_DISK_SIZE "8.8.8.8 1.1.1.1" STORAGE_PATH1 STORAGE_PATH2 ...
#
# Example:
# ./setup-vm.sh ubuntu-vm 123456 2048 2 ubuntu-24.04-server-cloudimg-amd64.img \
#   52:54:00:aa:bb:cc 192.168.122.50 192.168.122.1 24 30G \
#   "8.8.8.8 1.1.1.1" /mnt/storage1/libvirt/images /mnt/storage2/libvirt/images /var/lib/libvirt/images

# ==== Parameters ====
VM_NAME=$1
ROOT_PASS=$2
VM_RAM=$3
VM_VCPUS=$4
CLOUD_IMAGE_NAME=$5
VM_MAC=$6
VM_IP=$7
VM_GATEWAY=$8
CIDR=$9
VM_DISK_SIZE=${10}
shift 10
VM_DNS=($1)   # split space-separated DNS string into array
shift 1
STORAGE_PATHS=("$@")   # remaining args are storage paths

CLOUD_IMAGE_URL="https://cloud-images.ubuntu.com/releases/24.04/release/${CLOUD_IMAGE_NAME}"

# ==== Pick storage path ====
choose_storage() {
  local size_bytes
  size_bytes=$(numfmt --from=iec "$VM_DISK_SIZE" 2>/dev/null)
  if [[ -z "$size_bytes" ]]; then
    echo "[!] Invalid VM_DISK_SIZE: $VM_DISK_SIZE"
    return 1
  fi

  local size_required_kb=$((size_bytes / 1024))

  for path in "${STORAGE_PATHS[@]}"; do
    if [ -d "$path" ]; then
      free_kb=$(df -k --output=avail "$path" | tail -n1 | tr -d ' ')
      echo "[DEBUG] Checking $path: free=${free_kb}KB, need=${size_required_kb}KB"
      if [ "$free_kb" -ge "$size_required_kb" ]; then
        echo "$path"
        return 0
      fi
    fi
  done
  return 1
}

STORAGE_PATH="/var/lib/libvirt/nfs-pool"
#if [ -z "$STORAGE_PATH" ]; then
#  echo "[!] No storage path has enough space"
#  exit 1
#fi

BASE_IMAGE="${STORAGE_PATH}/${CLOUD_IMAGE_NAME}"
VM_DISK="${STORAGE_PATH}/${VM_NAME}.qcow2"
SEED_ISO="${STORAGE_PATH}/${VM_NAME}-seed.iso"

# ==== Download base image if missing ====
if [ ! -f "$BASE_IMAGE" ]; then
  echo "[+] Downloading base cloud image to $BASE_IMAGE ..."
  sudo wget "$CLOUD_IMAGE_URL" -O "$BASE_IMAGE"
fi

# ==== Create disk image for VM ====
if [ ! -f "$VM_DISK" ]; then
  echo "[+] Creating VM disk at $VM_DISK ..."
  sudo qemu-img create -f qcow2 -F qcow2 -b "$BASE_IMAGE" "$VM_DISK" "$VM_DISK_SIZE"
else
  echo "[+] VM disk already exists: $VM_DISK"
fi

# ==== Create cloud-init config ====
WORK_DIR=$(mktemp -d)
cd "$WORK_DIR" || exit 1

cat > user-data <<EOF
#cloud-config
users:
  - name: root
    shell: /bin/bash
    lock_passwd: false
    plain_text_passwd: '${ROOT_PASS}'
    sudo: ALL=(ALL) NOPASSWD:ALL

chpasswd:
  list: |
    root:${ROOT_PASS}
  expire: false

ssh_pwauth: true
disable_root: false

bootcmd:
  - echo "ttyS0" >> /etc/securetty

runcmd:
  - [ sed, -i, 's/^#\\?PasswordAuthentication.*/PasswordAuthentication yes/', /etc/ssh/sshd_config ]
  - [ sed, -i, 's/^#\\?PermitRootLogin.*/PermitRootLogin yes/', /etc/ssh/sshd_config ]
  - [ sed, -i, 's/^#\\?ChallengeResponseAuthentication.*/ChallengeResponseAuthentication no/', /etc/ssh/sshd_config ]
  - [ sed, -i, 's/^#\\?UsePAM.*/UsePAM yes/', /etc/ssh/sshd_config ]
  - [ systemctl, restart, ssh ]
EOF

echo "instance-id: $VM_NAME; local-hostname: $VM_NAME" > meta-data

cat > network-config <<EOF
version: 2
ethernets:
  eth0:
    match:
      macaddress: ${VM_MAC}
    set-name: eth0
    addresses:
      - ${VM_IP}/${CIDR}
    gateway4: ${VM_GATEWAY}
    nameservers:
      addresses: [$(IFS=,; echo "${VM_DNS[*]}")]
EOF

cloud-localds --network-config=network-config seed.iso user-data meta-data
sudo mv seed.iso "$SEED_ISO"

cd - >/dev/null || exit 1
rm -rf "$WORK_DIR"

# ==== Destroy existing VM if needed ====
echo "[+] Cleaning up old VM if exists..."
virsh destroy "$VM_NAME" &>/dev/null || true
virsh undefine "$VM_NAME" --remove-all-storage &>/dev/null || true

# ==== Return full paths ====
echo "$VM_DISK $SEED_ISO"
