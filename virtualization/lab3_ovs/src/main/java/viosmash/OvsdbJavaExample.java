package viosmash;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class OvsdbJavaExample {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1"; // ovsdb-server IP
        int port = 6640; // default OVSDB TCP port

        try (Socket socket = new Socket(host, port)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // Step 1: Create a UUID for the new bridge
            String newBridgeUuid = UUID.randomUUID().toString();

            // Step 2: Send "transact" to insert new bridge
            String transactCmd = String.format("""
                {
                  "method": "transact",
                  "params": [
                    "Open_vSwitch",
                    {
                      "op": "insert",
                      "table": "Bridge",
                      "row": {
                        "name": "br-test"
                      },
                      "uuid-name": "new_bridge"
                    },
                    {
                      "op": "mutate",
                      "table": "Open_vSwitch",
                      "where": [],
                      "mutations": [
                        ["bridges", "insert", ["set", [["named-uuid", "new_bridge"]]]]
                      ]
                    }
                  ],
                  "id": 1
                }
                """);

            writer.write(transactCmd);
            writer.flush();

            // Step 3: Read response
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Response: " + line);
                if (line.contains("\"id\": 1")) {
                    break; // stop after response
                }
            }
        }
    }
}
