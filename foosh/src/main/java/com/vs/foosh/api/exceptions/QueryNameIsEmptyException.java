package com.vs.foosh.api.exceptions;

import java.util.Map;

public class QueryNameIsEmptyException extends RuntimeException {
    private String id;
    public QueryNameIsEmptyException(String id, Map<String, String> requestBody) {
        super("The provided value for the field 'queryName' (" + requestBody.get("queryName").toString() + ") is empty!");
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
