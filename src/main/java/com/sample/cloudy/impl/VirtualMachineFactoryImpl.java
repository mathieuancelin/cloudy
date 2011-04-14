package com.sample.cloudy.impl;

import com.sample.cloudy.api.vm.VirtualMachine;
import com.sample.cloudy.api.vm.VirtualMachineFactory;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualMachineFactoryImpl implements VirtualMachineFactory {

    private static final String ADAPTER = "en1: AirPort";

    @Override
    public VirtualMachine create(String name, String address, URL golden, URL repo, URL path) {
        System.out.println("Creating virtual machine : " + name);
        create(name, repo, golden);
        return new VirtualMachineImpl(name, address, golden, path);
    }

    private void create(String name, URL repo, URL golden) {
        String UUID = null;
        File file = new File(repo.getFile() + "/" + name);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Process p = Runtime.getRuntime().exec("VBoxManage clonevdi " 
                    + golden.getFile()
                    + " " + repo.getFile() + "/"
                    + name
                    + "/"
                    + name
                    + ".vdi");
            String ret = VirtualMachineImpl.convertStreamToString(p.getInputStream());
            String[] vals = ret.split("UUID: ");
            if (vals.length == 2) {
                UUID = vals[1];
            } else {
                throw new RuntimeException("can't catch UUID");
            }
            p = Runtime.getRuntime().exec("VBoxManage createvm -name " + name + " -ostype Ubuntu -register");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --memory 1024");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --nic1 nat");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --nictype1 82540EM");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --cableconnected1 on");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));

            String disk = "VBoxManage modifyvm " + name + " --sata on --sataport1 "+ repo.getFile()
                    + name + "/" + name + ".vdi --sataportcount 2";
            p = Runtime.getRuntime().exec(disk);
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --boot1 disk");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
            p = Runtime.getRuntime().exec("VBoxManage modifyvm " + name + " --bioslogodisplaytime 0");
            p.waitFor();
            //System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));

//            String adapt = "VBoxManage modifyvm " + name + " --bridgeadapter1 \"" + ADAPTER + "\"";
//            System.out.println(adapt);
//            p = Runtime.getRuntime().exec(adapt);
//            p.waitFor();
//            System.out.println(VirtualMachineImpl.convertStreamToString(p.getInputStream()));
        } catch (Exception ex) {
            Logger.getLogger(VirtualMachineImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
