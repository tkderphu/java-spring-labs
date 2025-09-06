#!/bin/bash

# ==== Configurable Variables ====
VM_NAME="${1:-ubuntu-vm-1}"
ROOT_PASS="${2:-123456}"
VM_RAM=2048
VM_VCPUS=2
CLOUD_IMAGE_NAME="ubuntu-24.04-server-cloudimg-amd64.img"
CLOUD_IMAGE_URL="https://cloud-images.ubuntu.com/releases/24.04/release/${CLOUD_IMAGE_NAME}"
BASE_IMAGE="/var/lib/libvirt/images/${CLOUD_IMAGE_NAME}"
VM_DISK="/var/lib/libvirt/images/${VM_NAME}.qcow2"
SEED_ISO="/var/lib/libvirt/images/${VM_NAME}-seed.iso"

# Static networking
VM_MAC="52:54:00:ab:bb:cc"   # Change as needed
VM_IP="192.168.50.102"
VM_GATEWAY="192.168.50.1"
VM_NETMASK="255.255.255.0"
VM_DNS="8.8.8.8"

# ==== Install dependencies ====
echo "[+] Installing dependencies..."
#sudo apt update
#sudo apt install -y qemu-kvm libvirt-daemon-system virtinst cloud-image-utils genisoimage net-tools

# ==== Download base image if missing ====
if [ ! -f "$BASE_IMAGE" ]; then
  echo "[+] Downloading base cloud image..."
  sudo wget "$CLOUD_IMAGE_URL" -O "$BASE_IMAGE"
fi

# ==== Create disk image for VM ====
echo "[+] Creating disk for $VM_NAME..."
sudo cp "$BASE_IMAGE" "$VM_DISK"

# ==== Create cloud-init config ====
WORK_DIR=$(mktemp -d)
cd "$WORK_DIR"

echo "[+] Creating cloud-init config for $VM_NAME..."
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

# ==== ADDED: network-config for static IP ====
cat > network-config <<EOF
version: 2
ethernets:
  eth0:
    match:
      macaddress: ${VM_MAC}
    set-name: eth0
    addresses:
      - ${VM_IP}/24
    gateway4: ${VM_GATEWAY}
    nameservers:
      addresses: [${VM_DNS}]
EOF

cloud-localds --network-config=network-config seed.iso user-data meta-data
sudo mv seed.iso "$SEED_ISO"

# ==== Destroy existing VM if needed ====
echo "[+] Cleaning up old VM if exists..."
virsh destroy "$VM_NAME" &>/dev/null || true
virsh undefine "$VM_NAME" --remove-all-storage &>/dev/null || true

# ==== Create the VM ====
echo "[+] Creating VM $VM_NAME..."
sudo virt-install \
  --name "$VM_NAME" \
  --memory "$VM_RAM" \
  --vcpus "$VM_VCPUS" \
  --disk path="$VM_DISK",format=qcow2 \
  --disk path="$SEED_ISO",device=cdrom \
  --os-variant ubuntu20.04 \
  --network network=ovs-net,model=virtio,mac=52:54:00:ab:bb:cc \
  --import \
  --noautoconsole
# ==== Show IP Address ====
echo "[+] Waiting for VM to get IP..."
sleep 10
virsh domifaddr "$VM_NAME" || echo "❗ Couldn't get IP address. Try: virsh console $VM_NAME"

# ==== Done ====
echo "✅ VM '$VM_NAME' created. Static IP: ${VM_IP} | MAC: ${VM_MAC} | Password: ${ROOT_PASS}"

# ==== Cleanup ====
rm -rf "$WORK_DIR"
