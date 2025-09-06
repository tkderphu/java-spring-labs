package site.viosmash;

import com.jcraft.jsch.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RemoteVmSetup {
    public static void main(String[] args) throws IOException {
        String user = "root";
        String host = "192.168.10.135";   // your KVM/libvirt server
        int port = 22;
        String password = "root";

        // ==== VM parameters ====
        String vmName = "test-ubuntu-vm";
        String rootPass = "123456";
        String ram = "2048"; // MB
        String vcpus = "2";
        String cloudImg = "ubuntu-24.04-server-cloudimg-amd64.img";
        String mac = "52:54:00:aa:bb:cc";
        String ip = "192.168.122.50";
        String gateway = "192.168.122.1";
        String cidr = "24";
        String diskSize = "2G";  // <-- new parameter
        String dns = "8.8.8.8 1.1.1.1"; // space-separated
        String storagePaths = "/var/lib/libvirt/nfs-pool";

        // Local script (inside resources)
        InputStream scriptStream = RemoteVmSetup.class
                .getClassLoader()
                .getResourceAsStream("scripts/fullScript.sh");

        if (scriptStream == null) {
            throw new RuntimeException("Script not found in resources");
        }

// Write to temp file
        Path tempFile = Files.createTempFile("setup-vm", ".sh");
        Files.copy(scriptStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        scriptStream.close();

        String localScript = tempFile.toAbsolutePath().toString();
        // Remote script location
        String remoteScript = "/tmp/setup-vm.sh";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // ==== Copy script to remote ====
            scpToRemote(session, localScript, remoteScript);
            System.out.println("[+] Copied script to " + remoteScript);

            // ==== Build command ====
            String command = String.format(
                    "chmod +x %s && %s" , remoteScript, remoteScript);

            System.out.println("[+] Running: " + command);

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("Exit status: " + channel.getExitStatus());
                    break;
                }
                Thread.sleep(500);
            }

            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scpToRemote(Session session, String localFile, String remoteFile) throws Exception {
        boolean ptimestamp = true;
        FileInputStream fis = null;
        String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + remoteFile;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.connect();

        if (checkAck(in) != 0) {
            throw new IOException("SCP failed at start");
        }

        File _lfile = new File(localFile);

        if (ptimestamp) {
            command = "T" + (_lfile.lastModified() / 1000) + " 0";
            command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                throw new IOException("SCP timestamp failed");
            }
        }

        long filesize = _lfile.length();
        command = "C0644 " + filesize + " ";
        if (localFile.lastIndexOf('/') > 0) {
            command += localFile.substring(localFile.lastIndexOf('/') + 1);
        } else {
            command += localFile;
        }
        command += "\n";
        out.write(command.getBytes());
        out.flush();
        if (checkAck(in) != 0) {
            throw new IOException("SCP file metadata failed");
        }

        fis = new FileInputStream(localFile);
        byte[] buf = new byte[1024];
        while (true) {
            int len = fis.read(buf, 0, buf.length);
            if (len <= 0) break;
            out.write(buf, 0, len);
        }
        fis.close();
        fis = null;
        out.write(0);
        out.flush();
        if (checkAck(in) != 0) {
            throw new IOException("SCP file data failed");
        }
        out.close();

        channel.disconnect();
    }

    private static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        if (b == 0) return b;
        if (b == -1) return b;
        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            throw new IOException("SCP error: " + sb.toString());
        }
        return b;
    }
}
