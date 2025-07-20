package com.viosmash.virtualMachine;

import java.util.List;

public class Network {
    private Long id;
    private String nameNetwork;
    private String networkAddress;
    private IPVersion ipVersion;
    private String ipGateway;

    private List<String> allocationPool;
    private List<String> dnsServers;


    public static enum IPVersion {
        IP_V4,
        IP_V6
    }
}
