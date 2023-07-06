package com.vs.foosh.api.exceptions;

import com.vs.foosh.api.model.QueryNamePatchRequest;

public class BatchQueryNameException extends RuntimeException {
    public BatchQueryNameException() {
        super("Could not execute batch query name update!");
    }
}
