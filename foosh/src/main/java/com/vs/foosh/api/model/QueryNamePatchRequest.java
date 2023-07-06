package com.vs.foosh.api.model;

public class QueryNamePatchRequest {
    private String id;
    private String queryName;

    public QueryNamePatchRequest(String id, String queryName) {
        this.id = id;
        this.queryName = queryName;
    }

    public String getId() {
        return this.id;
    }

    public String getQueryName() {
        return this.queryName;
    }
}
