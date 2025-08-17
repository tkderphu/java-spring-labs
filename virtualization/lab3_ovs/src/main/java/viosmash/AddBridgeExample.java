package viosmash;

import com.vmware.ovsdb.exception.OvsdbClientException;
import com.vmware.ovsdb.protocol.operation.Insert;
import com.vmware.ovsdb.protocol.operation.Mutate;
import com.vmware.ovsdb.protocol.operation.Operation;
import com.vmware.ovsdb.protocol.operation.notation.*;
import com.vmware.ovsdb.protocol.operation.result.OperationResult;
import com.vmware.ovsdb.service.OvsdbClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AddBridgeExample {
    public static void addBridge(OvsdbClient client, String bridgeName) throws Exception {

        Row bridgeRow = new Row().stringColumn("name", bridgeName);
        Insert insertBridge = new Insert("Bridge", bridgeRow).withUuidName("new_bridge");

        Mutate mutation = new Mutate("Open_vSwitch")
                .mutation("bridges", Mutator.INSERT, new NamedUuid("new_bridge"));

        // Build transaction
        List<Operation> ops = Arrays.asList(insertBridge, mutation);

        // Execute transaction
        OperationResult[] openVSwitches = client.transact("Open_vSwitch", ops)
                .get(3, TimeUnit.SECONDS);

        System.out.println("Bridge add results: " + openVSwitches);
    }


    public static void createPort(OvsdbClient client, String ifaceName, String portName, String bridgeName) throws OvsdbClientException, ExecutionException, InterruptedException, TimeoutException {
        // 1) Insert Interface row with name = ifaceName, named-uuid "iface"
        Row ifaceRow = new Row().stringColumn("name", ifaceName);
        Insert insertIface = new Insert("Interface", ifaceRow).withUuidName("iface");

        // 2) Insert Port row with name = portName, interfaces set containing the inserted interface UUID, named-uuid "port"
        Row portRow = new Row()
                .stringColumn("name", portName)
                .setColumn("interfaces", new HashSet<>(List.of(new NamedUuid("iface"))));
        Insert insertPort = new Insert("Port", portRow).withUuidName("port");

        // 3) Mutate Bridge to insert the Port UUID into ports column
        Mutate mutateBridge = new Mutate("Bridge")
                .where("name", Function.EQUALS, bridgeName)
                .mutation("ports", Mutator.INSERT, new NamedUuid("port"));

        // 4) Execute transaction
        OperationResult[] results = client.transact("Open_vSwitch", List.of(insertIface, insertPort, mutateBridge))
                .get(5, TimeUnit.SECONDS);

        for (OperationResult result : results) {
            System.out.println(result);
        }

    }

}
