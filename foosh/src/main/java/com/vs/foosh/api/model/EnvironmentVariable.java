package com.vs.foosh.api.model;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.vs.foosh.api.services.LinkBuilder;

// TODO: Add all necessary class/object fields
public class EnvironmentVariable {
    private final UUID id;
    private String name;
    private List<UUID> models;
    private List<UUID> devices;

    private List<URI> modelLinks;
    private List<URI> deviceLinks;
    private Map<String, URI> links;

    public EnvironmentVariable(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }


    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setModels(List<UUID> modelIDs) {
        this.models = modelIDs;
        updateModelLinks();
    }

    public void addModel(UUID modelID) {
        if (!this.models.contains(modelID)) {
            this.models.add(modelID);
            updateModelLinks();
        }
    }
    
    private void updateModelLinks() {
        modelLinks.clear();
        modelLinks.addAll(LinkBuilder.getModelBatchLink(this.models));
    }

    public List<URI> getModelLinks() {
        return this.modelLinks;
    }

    public void setDevices(List<UUID> deviceIDs) {
        this.devices = deviceIDs;
        updateDeviceLinks();
    }

    public void addDevice(UUID deviceID) {
        if (!this.devices.contains(deviceID)) {
            this.devices.add(deviceID);
            updateDeviceLinks();
        }
    }

    private void updateDeviceLinks() {
        deviceLinks.clear();
        deviceLinks.addAll(LinkBuilder.getDeviceBatchLink(this.devices));
    }

    public List<URI> getDeviceLinks() {
        return this.deviceLinks;
    }

    public Map<String, URI> getLinks() {
        updateLinks();

        return links;
    }

    private void updateLinks() {
        links.put("vars", LinkBuilder.getVariableListLink());
        links.put("self", LinkBuilder.getVariableLink(this.id));
    }

}
