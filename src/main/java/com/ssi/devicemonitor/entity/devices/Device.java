package com.ssi.devicemonitor.entity.devices;

import javafx.scene.control.TextField;

public abstract class Device {
    private String name;
    private String status;
    private String manufacturer;
    private String deviceType;
    private String version;

    public Device(String name) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
    }

    public Device(String name, String manufacturer, String deviceType, String version) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.deviceType = deviceType;
        this.version = version;
        this.status = "Offline"; // Set initial status to Offline
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getDeviceType() {
        return deviceType;
    }
    public String getVersion() {
        return version;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
