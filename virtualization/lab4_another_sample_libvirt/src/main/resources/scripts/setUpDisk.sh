#!/bin/bash

# ==== Usage ====
# ./create-vm-disk.sh VM_NAME DISK_SIZE CLOUD_IMAGE_NAME STORAGE_PATH1 STORAGE_PATH2 ...
#
# Example:
# ./create-vm-disk.sh myvm 20G ubuntu-24.04-server-cloudimg-amd64.img \
#   /mnt/storage1/libvirt/images /mnt/storage2/libvirt/images /var/lib/libvirt/images

# ==== Configurable Variables ====
VM_NAME=$1
DISK_SIZE=$2   # e.g. 20G
CLOUD_IMAGE_NAME=$3
CLOUD_IMAGE_URL="https://cloud-images.ubuntu.com/releases/24.04/release/${CLOUD_IMAGE_NAME}"

# Shift first 3 args → remaining args are storage paths
shift 3
STORAGE_PATHS=("$@")

# ==== Choose storage with more free space ====
BEST_PATH=""
MAX_FREE=0

for path in "${STORAGE_PATHS[@]}"; do
  if [ -d "$path" ]; then
    FREE=$(df --output=avail -B1 "$path" | tail -n1)
    if [ "$FREE" -gt "$MAX_FREE" ]; then
      MAX_FREE=$FREE
      BEST_PATH=$path
    fi
  fi
done

if [ -z "$BEST_PATH" ]; then
  echo "[-] No valid storage path found. Exiting." >&2
  exit 1
fi

# ==== Paths ====
BASE_IMAGE="${BEST_PATH}/${CLOUD_IMAGE_NAME}"
VM_DISK="${BEST_PATH}/${VM_NAME}.qcow2"

echo "[+] Selected storage: $BEST_PATH (Free: $((MAX_FREE/1024/1024)) MB)"
echo "[+] Base image: $BASE_IMAGE"
echo "[+] VM disk: $VM_DISK"

# ==== Download base image if missing ====
if [ ! -f "$BASE_IMAGE" ]; then
  echo "[+] Downloading base cloud image to $BASE_IMAGE..."
  sudo wget -q "$CLOUD_IMAGE_URL" -O "$BASE_IMAGE"
fi

# ==== Create VM disk with backing file ====
if [ ! -f "$VM_DISK" ]; then
  echo "[+] Creating VM disk $VM_DISK with size $DISK_SIZE..."
  qemu-img create -f qcow2 -F qcow2 -b "$BASE_IMAGE" "$VM_DISK" "$DISK_SIZE"
else
  echo "[+] Disk $VM_DISK already exists. Skipping creation."
fi

# ==== Return full path ====
echo "$VM_DISK"