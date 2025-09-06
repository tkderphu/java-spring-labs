#! /bin/bash

VETH=$1
VETH_PEER=$2
OVS_BRIDGE=$3
ip link set $VETH type veth peer name $VETH_PEER
ip link set $ 
ovs-vsctl add-port $OVS_BRIDGE $VETH
ovs-vsctl add-port $OVS_BRIDGE $VETH_PEER