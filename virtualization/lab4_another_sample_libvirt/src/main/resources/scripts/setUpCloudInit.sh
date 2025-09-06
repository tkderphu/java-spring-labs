#! /bin/bash

VM_NAME="${1:-ubuntu-vm}"
ROOT_PASS="${2:-123456}"
VM_RAM=2048
VM_VCPUS=2
CLOUD_IMAGE_NAME="ubuntu-24.04-server-cloudimg-amd64.img"
CLOUD_IMAGE_URL="https://cloud-images.ubuntu.com/releases/24.04/release/${CLOUD_IMAGE_NAME}"
BASE_IMAGE="/var/lib/libvirt/images/${CLOUD_IMAGE_NAME}"
VM_DISK="/var/lib/libvirt/images/${VM_NAME}.qcow2"
SEED_ISO="/var/lib/libvirt/images/${VM_NAME}-seed.iso"

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

cloud-localds seed.iso user-data meta-data
sudo mv seed.iso "$SEED_ISO"

