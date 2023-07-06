package com.vs.foosh.api.exceptions;

public class QueryNameIsNotUniqueException extends RuntimeException {
    private String id;

    public QueryNameIsNotUniqueException(String id, String name) {
        super("The query name '" + name + "' is not unique!");
        this.id = id;
    }

    public String getId() { return this.id; }
}
