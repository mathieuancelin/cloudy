package com.sample.cloudy.impl;

import com.sample.cloudy.api.Health;
import com.sample.cloudy.api.Status;
import com.sample.cloudy.api.node.AppNode;
import com.sample.cloudy.api.vm.VirtualMachine;
import java.net.URL;

public class AppNodeImpl implements AppNode {

    private final VirtualMachine virtualMachine;
    private final String name;
    private final String address;
    private final URL golden;
    private final URL path;
    private final URL repo;
    private Status status = Status.STOPPED;

    AppNodeImpl(String name, String address, URL golden, URL path, URL repo) {
        this.name = name;
        this.address = address;
        this.golden = golden;
        this.path = path;
        this.repo = repo;
        virtualMachine = create();
    }

    private VirtualMachine create() {
        VirtualMachine machine = new VirtualMachineFactoryImpl().create(
                                                        name,
                                                        address,
                                                        golden,
                                                        repo,
                                                        path);
        return machine;
    }

    @Override
    public VirtualMachine virtualMachine() {
        return virtualMachine;
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
    public boolean start() {
        System.out.println("Starting virtual machine : " + name);
        status = Status.STARTED;
        return virtualMachine.start();
    }

    @Override
    public boolean stop() {
        System.out.println("Stopping virtual machine : " + name);
        status = Status.STOPPED;
        return virtualMachine.stop();
    }

    @Override
    public boolean delete() {
        return virtualMachine.delete();
    }

    @Override
    public boolean deploy(String name, URL path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean undeploy(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
