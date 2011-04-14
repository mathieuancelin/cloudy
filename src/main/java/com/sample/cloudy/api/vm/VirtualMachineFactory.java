package com.sample.cloudy.api.vm;

import java.net.URL;

public interface VirtualMachineFactory {

    VirtualMachine create(String name, String address, URL golden, URL repo, URL path);
}
