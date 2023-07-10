package com.vs.foosh.api.exceptions;

public class CouldNotFindUniqueQueryNameException extends RuntimeException {
    private String id;
    private int timeoutCount;

    public CouldNotFindUniqueQueryNameException(String id, int timeoutCount) {
        super("Could not find an unique queryName for device " + id + " after " + timeoutCount + " tries.");
        this.id = id;
        this.timeoutCount = timeoutCount;
    }

    public String getId() {
        return this. id;
    }

    public int getTimeoutCount() {
        return this.timeoutCount;
    }
}
