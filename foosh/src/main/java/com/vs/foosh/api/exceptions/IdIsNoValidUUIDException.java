package com.vs.foosh.api.exceptions;

public class IdIsNoValidUUIDException extends RuntimeException {
    
    public IdIsNoValidUUIDException(String id) {
        super("The provided id '" + id + "' is not a valid UUID!");
    }
}
