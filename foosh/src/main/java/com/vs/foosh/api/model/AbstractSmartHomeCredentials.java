package com.vs.foosh.api.model;

import java.util.HashMap;

public abstract class AbstractSmartHomeCredentials {

    protected String uri;
    protected HashMap<String, String> credentials;

    public AbstractSmartHomeCredentials() {}

    public AbstractSmartHomeCredentials(HashMap<String, String> credentials) {
        setUri(uri);
        setCredentials(credentials);
    }

    public abstract void loadSmartHomeCredentials();

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setCredentials(HashMap<String, String> credentials) {
        this.credentials = credentials;
    }

    public HashMap<String, String> getCredentials() {
        return this.credentials;
    }
}
