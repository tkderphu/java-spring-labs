package com.viosmash.virtualMachine;

public class FirewallRules {
    private Long id;
    private Protocol protocol;


    private Integer fromPort;
    private Integer toPort;

    private FilterType filterType;

    private OperationTable operationTable;



    public static enum OperationTarget {
        ACCEPT, DROP, MASQUERADE
    }

    public static enum OperationTable {
        NAT, FILTER
    }

    public static enum FilterType {
        INPUT, FORWARD, OUTPUT
    }

    public static enum Protocol {
        TCP,
        UDP,
        ICMP
    }
}
