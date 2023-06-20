package com.ssi.devicemonitor.entity.devices;

import javafx.scene.control.TextField;

public class HardwareDevice extends Device {
    private String loc;
    private String macAddress;


    public HardwareDevice(String name, String manufacturer, String deviceType, String version, String loc, String macAddress) {
        super(name, manufacturer, deviceType, version);
        this.loc = loc;
        this.macAddress = macAddress;
    }

    public String getLoc() {
        return loc;
    }
    public String getMacAddress() {
        return macAddress;
    }
}
