package com.sample.cloudy.api.app;

import java.net.URL;

public interface CloudAppFactory {

    CloudApp createCloudApp(String name, URL deployed, URL repo, URL goldenImage, IpRange range);
}
