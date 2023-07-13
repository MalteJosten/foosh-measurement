package com.vs.foosh.api.exceptions;

import java.util.UUID;

import com.vs.foosh.api.model.QueryNamePatchRequest;

public class QueryNameIsEmptyException extends RuntimeException {
    private QueryNamePatchRequest request;

    public QueryNameIsEmptyException(QueryNamePatchRequest request) {
        super("The provided value for the field 'queryName' (" + request.getQueryName() + ") is empty!");
        this.request = request;
    }

    public UUID getId() {
        return this.request.getId();
    }
}
