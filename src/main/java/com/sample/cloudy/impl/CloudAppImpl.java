package com.sample.cloudy.impl;

import com.sample.cloudy.api.Status;
import com.sample.cloudy.api.app.CloudApp;
import com.sample.cloudy.api.app.IpRange;
import com.sample.cloudy.api.node.AppNode;
import com.sample.cloudy.api.node.AppNodeFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CloudAppImpl implements CloudApp {

    private final IpRange range;
    private final String name;
    private final URL deployed;
    private final URL goldenImage;
    private final URL repo;
    private final List<AppNode> nodes = new ArrayList<AppNode>();
    private final int max;
    private int count;

    CloudAppImpl(IpRange range, String name, URL deployed, URL goldenImage, URL repo) {
        this.range = range;
        this.name = name;
        this.deployed = deployed;
        this.goldenImage = goldenImage;
        this.repo = repo;
        count = Integer.valueOf(range.getIpStart().split("\\.")[3]);
        max = Integer.valueOf(range.getIpStop().split("\\.")[3]);
    }

    @Override
    public IpRange ipRange() {
        return range;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public URL deployed() {
        return deployed;
    }

    @Override
    public URL goldenImage() {
        return goldenImage;
    }

    @Override
    public Collection<AppNode> nodes() {
        return nodes;
    }

    @Override
    public boolean start() {
        System.out.println("Starting cloud app. : " + name);
        if (nodes.isEmpty()) {
            boolean created = addNode();
            created = created && startNode();
            return created;
        } else {
            boolean success = true;
            for (AppNode node : nodes) {
                success = success && node.start();
            }
            return success;
        }
    }

    @Override
    public boolean stop() {
        System.out.println("Stopping cloud app. : " + name);
        boolean success = true;
        for (AppNode node : nodes) {
            success = success && node.stop();
        }
        return success;
    }

    @Override
    public boolean addNode() {
        System.out.println("Adding node : " + name + (nodes.size() + 1) + " to cloud app. : " + name);
        AppNodeFactory factory = new AppNodeFactoryImpl();
        AppNode node = factory.createAppNode(name + (nodes.size() + 1), getNextIP(), repo, goldenImage, deployed);
        nodes.add(node);
        return true;
    }

    @Override
    public boolean removeNode() {
        System.out.println("Removing node to cloud app. : " + name);
        if (!nodes.isEmpty()) {
            boolean success = true;
            AppNode node = nodes.iterator().next();
            success = success && node.stop();
            success = success && node.delete();
            nodes.remove(node);
            return success;
        }
        return false;
    }

    private String getNextIP() {
        if (count != max) {
            count++;
            String[] parts = range.getIpStart().split("\\.");
            return parts[0] + "." + parts[1] + "." + parts[2] + "." + count;
        } else {
            throw new IllegalStateException("Can't get more IP address");
        }
    }

    @Override
    public boolean startNode() {
        AppNode startable = null;
        for (AppNode node : nodes) {
            if (node.status().equals(Status.STOPPED)) {
                startable = node;
                break;
            }
        }
        if (startable != null) {
            return startable.start();
        } else {
            addNode();
            return startNode();
        }
    }

    @Override
    public boolean stopNode() {
        AppNode stoppable = null;
        for (AppNode node : nodes) {
            if (node.status().equals(Status.STARTED)) {
                stoppable = node;
                break;
            }
        }
        if (stoppable != null) {
            return stoppable.stop();
        }
        return false;
    }
}
