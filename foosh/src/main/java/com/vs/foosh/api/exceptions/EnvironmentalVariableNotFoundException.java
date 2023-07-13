package com.vs.foosh.api.exceptions;

public class EnvironmentalVariableNotFoundException extends RuntimeException {
    private String id;

    public EnvironmentalVariableNotFoundException(String id) {
        super("Could not find environmental variable with id '" + id + "'!");
        this.id = id;
    }

    public String getId() { return this.id; }
}
