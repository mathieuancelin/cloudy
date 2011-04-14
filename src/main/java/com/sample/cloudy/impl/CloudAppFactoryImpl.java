package com.sample.cloudy.impl;

import com.sample.cloudy.api.app.CloudApp;
import com.sample.cloudy.api.app.CloudAppFactory;
import com.sample.cloudy.api.app.IpRange;
import java.net.URL;

public class CloudAppFactoryImpl implements CloudAppFactory {

    @Override
    public CloudApp createCloudApp(String name, URL deployed, URL repo, URL goldenImage, IpRange range) {
        return new CloudAppImpl(range, name, deployed, goldenImage, repo);
    }

}
