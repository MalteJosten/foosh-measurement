package com.vs.foosh.api.exceptions;

import com.vs.foosh.api.model.QueryNamePatchRequest;

public class QueryNameIsNotUniqueException extends RuntimeException {
    private QueryNamePatchRequest request;

    public QueryNameIsNotUniqueException(QueryNamePatchRequest request) {
        super("The query name '" + request.getQueryName() + "' is not unique!");
        this.request = request;
    }

    public String getId() {
        return this.request.getId();
    }
}
