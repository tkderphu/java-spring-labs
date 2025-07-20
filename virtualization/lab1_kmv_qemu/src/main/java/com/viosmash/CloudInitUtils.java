package com.viosmash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class CloudInitUtils {

    public static Path cloneAndResizeImage(Path baseImg, String newName, long extraGigabytes)
            throws IOException, InterruptedException {

        Path target = baseImg.resolveSibling(newName);

        // Copy base image to new file
        Files.copy(baseImg, target, StandardCopyOption.REPLACE_EXISTING);

        // Resize if needed
        if (extraGigabytes > 0) {
            // Add quotes around the path to handle spaces
            String command = String.format("qemu-img resize \"%s\" +%dG", target.toString(), extraGigabytes);
            CommandService.runCommand(command);
        }

        return target;
    }


    /** build user‑data YAML */
    public static String buildUserData(String rootPass) {
        return """
            #cloud-config
            hostname: cloudvm
            users:
              - name: root
                shell: /bin/bash
                lock_passwd: false
                plain_text_passwd: '%s'
                sudo: ALL=(ALL) NOPASSWD:ALL
            chpasswd:
              list: |
                root:%s
              expire: false
            ssh_pwauth: true
            disable_root: false
            bootcmd:
              - echo "ttyS0" >> /etc/securetty
            runcmd:
              - [ sed, -i, 's/^#\\\\?PasswordAuthentication.*/PasswordAuthentication yes/', /etc/ssh/sshd_config ]
              - [ sed, -i, 's/^#\\\\?PermitRootLogin.*/PermitRootLogin yes/', /etc/ssh/sshd_config ]
              - [ sed, -i, 's/^#\\\\?ChallengeResponseAuthentication.*/ChallengeResponseAuthentication no/', /etc/ssh/sshd_config ]
              - [ sed, -i, 's/^#\\\\?UsePAM.*/UsePAM yes/', /etc/ssh/sshd_config ]
              - [ systemctl, restart, ssh ]
            """.formatted(rootPass, rootPass);
    }

    /** build meta‑data YAML */
    public static String buildMetaData() {
        return """
            instance-id: cloudvm
            local-hostname: cloudvm
            """;
    }

    /** create the ISO using genisoimage */
    public static Path buildSeedIso(String rootPass) throws IOException, InterruptedException {
        Path seedData = Files.createTempDirectory("seed-data");
        Path userData = Files.writeString(seedData.resolve("user-data"), buildUserData(rootPass));
        Path metaData = Files.writeString(seedData.resolve("meta-data"), buildMetaData());
        Path isoPath = seedData.resolve("seed.iso");

        String command = String.format("cloud-localds %s %s %s",
                isoPath,
                userData,
                metaData);
        CommandService.runCommand(command);
        return isoPath;
    }
}
