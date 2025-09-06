#!/bin/bash

# Variables
USER="remoteuser"
HOST="192.168.1.100"
COMMAND="echo Hello from remote && uname -a"

# Run SSH
ssh ${USER}@${HOST} "${COMMAND}"