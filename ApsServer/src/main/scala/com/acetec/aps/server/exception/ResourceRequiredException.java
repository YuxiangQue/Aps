package com.acetec.aps.server.exception;

public class ResourceRequiredException extends RuntimeException {
    private final String resourceName;

    public ResourceRequiredException(String resourceName) {
        this.resourceName = resourceName;
    }
}
