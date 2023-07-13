package com.vs.foosh.api.exceptions;

import java.net.URI;
import java.util.Map;

public class HttpMappingNotImplementedException extends RuntimeException {
    private String message;
    private Map<String, URI> returnPath;

    public HttpMappingNotImplementedException(String message) {
        this.message = message;
    }

    public HttpMappingNotImplementedException(String message, Map<String, URI> returnPath) {
        this.message    = message;
        this.returnPath = returnPath;
    }

    public String getMessage()  {
        return this.message;
    }
    
    public Map<String, URI> getReturnPath()  {
        return this.returnPath;
    }
}
