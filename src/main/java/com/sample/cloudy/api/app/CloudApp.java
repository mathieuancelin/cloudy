package com.sample.cloudy.api.app;

import com.sample.cloudy.api.node.AppNode;
import java.net.URL;
import java.util.Collection;

public interface CloudApp {
    IpRange ipRange();
    String name();
    URL deployed();
    URL goldenImage();
    Collection<AppNode> nodes();
    boolean start();
    boolean stop();
    boolean addNode();
    boolean removeNode();
    boolean startNode();
    boolean stopNode();
}
