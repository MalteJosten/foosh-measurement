package com.vs.foosh.api.exceptions;

import java.util.Map;

public class HttpMappingNotImplementedException extends RuntimeException {
    private String message;
    private Map<String, String> returnPath;

    public HttpMappingNotImplementedException(String message) {
        this.message = message;
    }

    public HttpMappingNotImplementedException(String message, Map<String, String> returnPath) {
        this.message    = message;
        this.returnPath = returnPath;
    }

    public String getMessage()  {
        return this.message;
    }
    
    public Map<String, String> getReturnPath()  {
        return this.returnPath;
    }
}
