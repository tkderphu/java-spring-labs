package viosmash;

import com.vmware.ovsdb.exception.OvsdbClientException;
import com.vmware.ovsdb.protocol.operation.*;
import com.vmware.ovsdb.protocol.operation.notation.*;
import com.vmware.ovsdb.protocol.operation.result.OperationResult;
import com.vmware.ovsdb.protocol.operation.result.SelectResult;
import com.vmware.ovsdb.protocol.schema.DatabaseSchema;
import com.vmware.ovsdb.service.OvsdbActiveConnectionConnector;
import com.vmware.ovsdb.service.OvsdbClient;
import com.vmware.ovsdb.service.impl.OvsdbActiveConnectionConnectorImpl;
import com.vmware.ovsdb.service.impl.OvsdbClientImpl;

import java.util.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

        // create connector (implementation provided by the lib)
        OvsdbActiveConnectionConnector connector =
                new OvsdbActiveConnectionConnectorImpl(exec);

        // connect to host:port (ovsdb-server listening on 6640)
        CompletableFuture<OvsdbClient> future = connector.connect("localhost", 6640);

        OvsdbClient client = future.get(5, TimeUnit.SECONDS); // wait for connect

//        // list DBs (async API returns CompletableFuture)
//        String[] dbs = client.listDatabases().get(3, TimeUnit.SECONDS);
//        System.out.println("Databases: " + Arrays.toString(dbs));

        // cleanup
//        client.; // or whatever close/shutdown method the client exposes

//        AddBridgeExample.addBridge(client, "vcl-that");

        UUID bridgeUuid = UUID.fromString("98205b3a-e70f-41fa-be7c-47b6864e86b3");

// Get the root UUID of the Open_vSwitch table
//        Select select = new Select("Open_vSwitch");
//        SelectResult selectResult = (SelectResult) client.transact("Open_vSwitch", List.of(select))
//                .get(3, TimeUnit.SECONDS)[0];
//
//
//        List<Row> ovsRows = selectResult.getRows();
//
//        System.out.println(ovsRows);
//
//        UUID rootUuid = ovsRows.get(0).getUuidColumn("_uuid").getUuid(); // usually only 1 row
//


//        CreateBridgeAndPort.createBridgeAndPort(client, "dcmmay", "fuck");

//            AddBridgeExample.addBridge(client, "quang-phu");

        AddBridgeExample.createPort(client,"hehe", "vas", "quang-phu");

        exec.shutdownNow();
    }



}
