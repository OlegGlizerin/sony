package com.ssi.devicemonitor.entity.devices;

import javafx.scene.control.TextField;

public class SoftwareDevice extends Device{
    private String installationDate;
    private String time;

    public SoftwareDevice(String name, String manufacturer, String deviceType, String version, String installationDate, String time) {
        super(name, manufacturer, deviceType, version);
        this.installationDate = installationDate;
        this.time = time;
    }

    public String getInstallationDate() {
        return installationDate;
    }
    public String getTime() {
        return time;
    }
}
