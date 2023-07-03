package com.vs.foosh.api.model.Exceptions;

public class QueryNameIsNotUniqueException extends RuntimeException {
    public QueryNameIsNotUniqueException(String name) {
        super("The queryName '" + name + "' must be unique but is already used.");
    }
}
