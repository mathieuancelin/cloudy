package com.sample.cloudy.api.node;

import java.net.URL;

public interface AppNodeFactory {

    AppNode createAppNode(String name, String address, URL repo, URL golden, URL path);
}
