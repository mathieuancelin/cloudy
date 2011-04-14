package com.sample.cloudy.impl;

import com.sample.cloudy.api.node.AppNode;
import com.sample.cloudy.api.node.AppNodeFactory;
import java.net.URL;

public class AppNodeFactoryImpl implements AppNodeFactory {

    @Override
    public AppNode createAppNode(String name, String address, URL repo, URL golden, URL path) {
        return new AppNodeImpl(name, address, golden, path, repo);
    }

}
