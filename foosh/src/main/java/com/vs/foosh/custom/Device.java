package com.vs.foosh.custom;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.vs.foosh.api.model.AbstractDevice;
import com.vs.foosh.api.services.LinkBuilder;

public class Device extends AbstractDevice {

    public Device(JsonNode description) {
        this.id          = UUID.randomUUID();
        this.description = new DeviceDescription(description);

        //TODO: Set unique queryName
        setQueryName(this.description.getProperties().get("name").toString());
        setObjectFields();

        this.self    = LinkBuilder.buildPath(List.of("device", this.id.toString()));
        this.devices = LinkBuilder.getDeviceListLink();
    }

    public Device(JsonNode description, String queryName) {
        this.id          = UUID.randomUUID();
        this.description = new DeviceDescription(description);

        //TODO: Check for uniqueness
        setQueryName(queryName);
        setObjectFields();

        this.self    = LinkBuilder.buildPath(List.of("device", this.id.toString()));
        this.devices = LinkBuilder.getDeviceListLink();
    }

    @Override
    protected void setObjectFields() {
        this.deviceName = this.description.getProperties().get("name").toString();
        this.type       = this.description.getProperties().get("type").toString();
    }
}