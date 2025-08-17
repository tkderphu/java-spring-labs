package viosmash;

import com.vmware.ovsdb.protocol.operation.*;
import com.vmware.ovsdb.protocol.operation.notation.*;
import com.vmware.ovsdb.protocol.operation.result.OperationResult;
import com.vmware.ovsdb.service.OvsdbClient;

import java.util.*;
import java.util.concurrent.*;

public class CreateBridgeAndPort {
    public static void createBridgeAndPort(OvsdbClient client, String bridgeName, String portName) throws Exception {
        // 1) Insert Bridge with a named-uuid "br0"
        Row bridgeRow = new Row().stringColumn("name", bridgeName);
        Insert insertBridge = new Insert("Bridge", bridgeRow).withUuidName("br0");


        // Build operations in the order we want executed
        List<Operation> ops = Arrays.asList(insertBridge);

        // Execute transaction against the Open_vSwitch DB
        OperationResult[] results = client.transact("Open_vSwitch", ops).get(5, TimeUnit.SECONDS);

        // Print results for each op
        for (OperationResult r : results) {
            System.out.println(r);
        }
    }
}
