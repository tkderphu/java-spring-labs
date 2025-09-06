package com.viosmash;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        try {
            Connect connect = new Connect("qemu+tcp://192.168.10.135/system", false);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your VM name: ");
            String vmName = scanner.nextLine();

            try {
                Domain dom = connect.domainLookupByName(vmName);

                if (dom != null && dom.isActive() == 1) {
                    dom.destroy();                    // equivalent to 'virsh destroy'
                    dom.undefine();
//                    dom.undefine(Domain.UndefineFlags.MANAGED_SAVE | Domain.UndefineFlags.SNAPSHOTS_METADATA);
                }
            } catch (Exception ex) {
                System.out.println("VM " + vmName + " hasn't exists yet.");
            }




//            Domain domain = connect.domainLookupByName("cloud-vm");
//            domain.destroy();
//            System.out.println("destroy cloud-vm");
//            Thread.sleep(10000);
//            System.out.println("start cloud-vm");
//            domain.create();
//            List<String> commands = List.of(
//                    String.format(
//                            "cloud-localds ~/cloud-vms/seed-%s.iso ~/cloud-vms/seed-data/user-data ~/cloud-vms/seed-data/meta-data",
//                            vmName
//                    ),
//                    String.format(
//                            "cd ~/cloud-vms && cp ubuntu-cloud.img ubuntu-cloud-vm-%s.qcow2 && qemu-img resize ubuntu-cloud-vm-%s.qcow2 +10G   # optional",
//                            vmName, vmName
//                    ),
//                    String.format(
//                            "mv ~/cloud-vms/ubuntu-cloud-vm-%s.qcow2 /var/lib/libvirt/images/",
//                            vmName
//                    ),
//                    String.format(
//                            "mv ~/cloud-vms/seed-%s.iso /var/lib/libvirt/images/",
//                            vmName
//                    )
//            );
//            commands.forEach(command -> {
//                String s = CommandService.runCommand(command);
//                System.out.println("[+]====>" + s);
//            });

            String xml = String.format("""
                            <domain type='qemu'>
                              <name>%s</name>
                              <memory unit='MiB'>2048</memory>
                              <vcpu placement='static'>2</vcpu>
                        
                              <os>
                                <type arch='x86_64' machine='pc'>hvm</type>
                                
                              </os>
                        
                              <features>
                                <acpi/>
                                <apic/>
                                <pae/>
                              </features>
                        
                              <cpu mode='host-model'>
                                <model fallback='allow'/>
                              </cpu>
                        
                              <devices>
                                <disk type='file' device='disk'>
                                  <driver name='qemu' type='qcow2'/>
                                  <source file='/var/lib/libvirt/images/fuck.qcow2'/>
                                  <target dev='vda' bus='virtio'/>
                                </disk>
                        
                                <disk type='file' device='cdrom'>
                                  <driver name='qemu' type='raw'/>
                                  <source file='/var/lib/libvirt/images/test-ubuntu-vm-seed.iso'/>
                                  <target dev='sda' bus='sata'/>
                                  <readonly/>
                                </disk>
                        
                                <interface type='network'>
                                  <mac address='52:54:00:ab:cd:cc'/>
                                  <source network='ovs-net'/>
                                  <model type='virtio'/>
                                </interface>
                        
                                <graphics type='vnc' port='-1' autoport='yes' listen='127.0.0.1'/>
                        
                                <console type='pty'>
                                  <target type='serial' port='0'/>
                                </console>
                              </devices>
                            </domain>
                        """, vmName, vmName, vmName);


            Domain domain = connect.domainDefineXML(xml);
            domain.create();

            System.out.println("Create vm successfully: " + vmName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
