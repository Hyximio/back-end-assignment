package com.mmbackendassignment.mmbackendassignment.exception;

public class NullValueNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NullValueNotAllowedException() {
        super("Null values are not allowed");
    }
}
