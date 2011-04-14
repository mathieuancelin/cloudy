package com.sample.cloudy.api.vm;

import com.sample.cloudy.api.Health;
import com.sample.cloudy.api.Status;
import java.net.URL;

public interface VirtualMachine {
    boolean start();
    boolean stop();
    boolean delete();
    Health health();
    Status status();
    String name();
    String address();
    String infos();
    URL goldenPath();
    URL path();
}
