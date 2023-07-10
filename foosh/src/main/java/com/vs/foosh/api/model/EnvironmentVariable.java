package com.vs.foosh.api.model;

import java.util.UUID;

// TODO: Add all necessary class/object fields
public class EnvironmentVariable {
    private UUID id;

    public EnvironmentVariable(String name) {
        this.id = UUID.randomUUID();

    }

    public UUID getId() {
        return this.id;
    }
}
