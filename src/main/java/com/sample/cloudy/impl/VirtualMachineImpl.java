package com.sample.cloudy.impl;

import com.sample.cloudy.api.Health;
import com.sample.cloudy.api.Status;
import com.sample.cloudy.api.vm.VirtualMachine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualMachineImpl implements VirtualMachine {

    private final String name;
    private final String address;
    private final URL golden;
    private final URL path;
    private Status status = Status.STOPPED;

    VirtualMachineImpl(String name, String address, URL golden, URL path) {
        this.name = name;
        this.address = address;
        this.golden = golden;
        this.path = path;
    }

    @Override
    public boolean start() {
        try {
            status = Status.STARTED;
            Process p = Runtime.getRuntime().exec("VBoxManage startvm " + name);
            p.waitFor();
            int exit = p.exitValue();
            return exit == 0;
        } catch (Exception ex) {
            Logger.getLogger(VirtualMachineImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean stop() {
        try {
            status = Status.STOPPED;
            Process p = Runtime.getRuntime().exec("VBoxManage controlvm " + name + " poweroff");
            p.waitFor();
            int exit = p.exitValue();
            return exit == 0;
        } catch (Exception ex) {
            Logger.getLogger(VirtualMachineImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete() {
        try {
            Process p = Runtime.getRuntime().exec("VBoxManage unregistervm -delete " + name);
            p.waitFor();
            int exit = p.exitValue();
            return exit == 0;
        } catch (Exception ex) {
            Logger.getLogger(VirtualMachineImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Health health() {
        return Health.GOOD;
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String infos() {
        try {
            Process p = Runtime.getRuntime().exec("VBoxManage showvminfo " + name);
            p.waitFor();
            return convertStreamToString(p.getInputStream());
        } catch (Exception ex) {
            Logger.getLogger(VirtualMachineImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    @Override
    public URL goldenPath() {
        return golden;
    }

    @Override
    public URL path() {
        return path;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
