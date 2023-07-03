package com.vs.foosh.api.model;

import java.net.URI;
import java.util.UUID;

import com.vs.foosh.services.LinkBuilder;

public class BaseDevice {
    private final UUID id;
    private String queryName;
    private String deviceName;
    private String type;
    private String description;
    private boolean turnedOn;
    private DeviceValue value;

    private final URI self;
    private URI devices;

    //TODO: Remove
    public BaseDevice() {
        this.id = UUID.randomUUID();
        this.self = LinkBuilder.buildPath("device", this.id);
    }

    public BaseDevice(String description, String queryName) {
        this.id          = UUID.randomUUID();
        this.queryName   = queryName;
        this.description = description;

        setObjectFields();

        this.self    = LinkBuilder.buildPath("device", this.id);
        this.devices = LinkBuilder.getDeviceListLink();
    }

    private void setObjectFields() {
        this.deviceName = "None";
        this.type       = "None";
        this.turnedOn   = false;
        this.value      = new DeviceValue();
    }

    public String getQueryName() {
        return this.queryName;
    }

    public void setQueryName(String name) {
        if (DeviceList.isAUniqueQueryName(name)) {
            this.queryName = name;
        }
    }
}