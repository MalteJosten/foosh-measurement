package com.vs.foosh.api.exceptions;

public class SmartHomeIOException extends RuntimeException {
    private String uri;

    public SmartHomeIOException(String uri) {
        super();
        this.uri = uri;
    }

    public String getUri() { return this.uri; }
    
}