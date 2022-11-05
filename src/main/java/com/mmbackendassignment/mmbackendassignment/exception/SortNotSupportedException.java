package com.mmbackendassignment.mmbackendassignment.exception;

public class SortNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SortNotSupportedException(String sort) {
        super("Sort type '" + sort + "' not supported");
    }
}
