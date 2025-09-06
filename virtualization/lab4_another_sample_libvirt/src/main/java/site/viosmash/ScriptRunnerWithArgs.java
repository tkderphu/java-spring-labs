package site.viosmash;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;

public class ScriptRunnerWithArgs {

    public static void main(String[] args) throws Exception {
        runScript("scripts/setUpVeth.sh", "Alice", "12345");
    }

    public static void runScript(String scriptPath, String... params) throws Exception {
        // Load resource from classpath
        ClassPathResource resource = new ClassPathResource(scriptPath);

        // Copy to temp file (because resources inside JAR are read-only)
        File tempFile = File.createTempFile("script-", ".sh");
        try (InputStream in = resource.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            in.transferTo(out);
        }
        tempFile.setExecutable(true);

        // Detect OS
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb;

        if (os.contains("win")) {
            // Windows - Git Bash
            pb = new ProcessBuilder("C:\\Program Files\\Git\\bin\\bash.exe", tempFile.getAbsolutePath());
        } else {
            // Linux/Mac
            pb = new ProcessBuilder("bash", tempFile.getAbsolutePath());
        }

        // Add parameters
        for (String p : params) {
            pb.command().add(p);
        }

        // Run script
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);
    }


    /**
     *
     * @param storagePath
     * @param cloudImageUrl
     */
    public void installCloudImage(String storagePath, String cloudImageUrl) {

    }

    /**
     *
     * @param size
     * @param volumeName
     * @param storagePaths
     */
    public void createVolume(int size, String volumeName, List<String> storagePaths) {

    }


    public void attachDisk() {

    }

    /**
     * sudo ovs-vsctl add-port br0 {ifaceName} \
     *     -- set interface {ifaceName} type=vxlan \
     *     options:remote_ip={remoteIps[i]} \
     *     options:key={vxlanId} \
     *     options:dst_port=4789
     * @param ifaceName
     * @param vxlanId
     * @param remoteIps: all server
     */
    public void createVxlanInterface(String ifaceName,
                                     int vxlanId,
                                     List<String> remoteIps) {

    }

    /**
     * router will be created in network-service
     * 1. ip netns add router-qrxxxx
     * 2. ip link set .... # set interface
     * 3. attach link to ovs and router
     * 4. update link enable
     * 5. update scripting(delete link in ovs before create link, router and attach to it)
     */
    public void createRouter() {

    }

    /**
     * 1. set subinterface
     * 2. set ip
     * 3. routing via rip
     * 4. set trunking in ovs
     * 5. update script(systemd) and send to network server
     */
    public void attachGateway(String ifaceName,int vxlanId, int vlanId, String gatewayIp) {

    }

    /**
     *
     * @param name
     * @param vRam
     * @param vCpu
     * @param rootDisk
     * @param ip
     * @param mac
     * @param cidr
     * @param vlanTag
     * @param vxlanTag
     * @param gateway
     * @param domainNameServers
     * @param password
     * @param sshKey
     * @param rootVolumePath
     * @param cloudInitPath
     */
    public void createNewVirtualMachine(
            String name,
            int vRam,
            int vCpu,
            int rootDisk,
            String ip,
            String mac,
            int cidr,
            int vlanTag,
            int vxlanTag,
            String gateway,
            List<String> domainNameServers,
            String password,
            String sshKey,
            String rootVolumePath,
            String cloudInitPath
    ) {

    }
}
