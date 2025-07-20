package com.viosmash;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandService {

    public static String runCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder( "ssh", "-i", "~/karina-key", "karina@192.168.1.3", command);
            builder.redirectErrorStream(true); // Combine stdout and stderr

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return output.toString();
    }
}
