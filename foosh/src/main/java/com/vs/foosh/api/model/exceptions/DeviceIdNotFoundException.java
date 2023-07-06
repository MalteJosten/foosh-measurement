package com.vs.foosh.api.model.exceptions;

public class DeviceIdNotFoundException extends RuntimeException {
    private String id;

    public DeviceIdNotFoundException(String id) {
        super("Could not find the device with id '" + id + "'!");
        this.id = id;
    }

    public String getId() { return this.id; }
}
