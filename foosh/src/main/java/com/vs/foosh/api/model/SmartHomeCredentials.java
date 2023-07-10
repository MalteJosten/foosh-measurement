package com.vs.foosh.api.model;

import java.util.HashMap;

public class SmartHomeCredentials {

    private String uri;
    private HashMap<String, String> credentials;

    public SmartHomeCredentials() {}

    public SmartHomeCredentials(String uri, HashMap<String, String> credentials) {
        setUri(uri);
        setCredentials(credentials);
    }

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
