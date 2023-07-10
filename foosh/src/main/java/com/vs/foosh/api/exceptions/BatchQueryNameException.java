package com.vs.foosh.api.exceptions;

public class BatchQueryNameException extends RuntimeException {
    public BatchQueryNameException() {
        super("Could not execute batch query name update!");
    }
}
