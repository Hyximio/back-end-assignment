package com.mmbackendassignment.mmbackendassignment.exception;

public class CantDeleteWithDependencyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CantDeleteWithDependencyException(String dependency) {
        super("Deletion aborted because of dependency: " + dependency);
    }
}

