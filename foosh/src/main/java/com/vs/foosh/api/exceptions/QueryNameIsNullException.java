package com.vs.foosh.api.exceptions;

import java.util.Map;

public class QueryNameIsNullException extends RuntimeException {
    private String id;
   
    public QueryNameIsNullException(String id, Map<String, String> requestBody) {
        super("The provided request body " + requestBody.toString() + " does not contain a field named 'queryName'!");
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
