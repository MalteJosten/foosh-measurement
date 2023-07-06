package com.vs.foosh.api.model;

import java.util.List;

public class FetchDeviceResponse {
    private boolean success;
    private String message;
    private List<AbstractDevice> devices;

    public FetchDeviceResponse(boolean success, List<AbstractDevice> devices) {
        this.success = success;
        this.devices = devices;
    }

    public FetchDeviceResponse(boolean success, String message, List<AbstractDevice> devices) {
        this.success = success;
        this.message = message;
        this.devices = devices;
    }

    public boolean getSucess() { return this.success; }

    public String getMessage() { return this.message; }

    public List<AbstractDevice> getDevices() { return this.devices; }
}
