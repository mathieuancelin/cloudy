package com.sample.cloudy.api.node;

import com.sample.cloudy.api.Health;
import com.sample.cloudy.api.Status;
import com.sample.cloudy.api.vm.VirtualMachine;
import java.net.URL;

public interface AppNode {
    VirtualMachine virtualMachine();
    Health health();
    Status status();
    boolean start();
    boolean stop();
    boolean delete();
    boolean deploy(String name, URL path);
    boolean undeploy(String name);
}
