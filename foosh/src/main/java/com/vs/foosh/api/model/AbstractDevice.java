package com.vs.foosh.api.model;

import java.util.Map;
import java.util.UUID;

import com.vs.foosh.api.exceptions.QueryNameIsNotUniqueException;

public abstract class AbstractDevice {
    protected UUID id;
    protected String queryName;
    protected String deviceName;
    protected String type;
    protected AbstractDeviceDescription description;

    protected Map<String, String> links;

    protected abstract void setObjectFields();

    public UUID getId() {
        return this.id;
    }

    public String getQueryName() {
        return this.queryName;
    }

    public void setQueryName(String name) {
        if (DeviceList.isAUniqueQueryName(name)) {
            this.queryName = name;
        } else {
            throw new QueryNameIsNotUniqueException(this.id.toString(), this.queryName);
        }
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getType() {
        return this.type;
    }

    public AbstractDeviceDescription getDescription() {
        return this.description;
    }

    public void setDescription(AbstractDeviceDescription description) {
        this.description = description;
    }

    public Map<String, String> getLinks() {
        return this.links;
    }

    @Override
    public String toString() {
        return "Device: " + id + "\nName: " + deviceName + "\nQuery-Name: " + queryName + "\nType: " + type
                + "\nlinks: " + links.toString();
    }
}