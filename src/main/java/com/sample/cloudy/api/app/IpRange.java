package com.sample.cloudy.api.app;

public class IpRange {

    private final String ipStart;
    private final String ipStop;

    public IpRange(String ipStart, String ipStop) {
        this.ipStart = ipStart;
        this.ipStop = ipStop;
    }

    public String getIpStart() {
        return ipStart;
    }

    public String getIpStop() {
        return ipStop;
    }
}
