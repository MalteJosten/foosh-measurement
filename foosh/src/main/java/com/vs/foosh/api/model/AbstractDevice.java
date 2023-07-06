package com.vs.foosh.api.model;

import java.net.URI;
import java.util.UUID;

public abstract class AbstractDevice {
    protected UUID id;
    protected String queryName;
    protected String deviceName;
    protected String type;
    protected AbstractDeviceDescription description;

    protected URI self;
    protected URI devices;

    protected abstract void setObjectFields();

    public String getQueryName() {
        return this.queryName;
    }

    public void setQueryName(String name) {
        if (DeviceList.isAUniqueQueryName(name)) {
            this.queryName = name;
        }
    }

    public UUID getId() {
        return this.id;
    }

    public AbstractDeviceDescription getDescription() {
        return this.description;
    }

    public void setDescription(AbstractDeviceDescription description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Device: " + id + "\nName: " + deviceName + "\nQuery-Name: " + queryName + "\nType: " + type
                + "\nselfLink: " + self.toString() + "\nDevices: " + devices.toString();
    }
}