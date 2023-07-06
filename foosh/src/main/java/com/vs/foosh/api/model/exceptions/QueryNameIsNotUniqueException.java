package com.vs.foosh.api.model.exceptions;

public class QueryNameIsNotUniqueException extends RuntimeException {
    private String id;

    public QueryNameIsNotUniqueException(String id, String name) {
        super("The queryName '" + name + "' must be unique but is already used.");
        this.id = id;
    }

    public String getId() { return this.id; }
}
