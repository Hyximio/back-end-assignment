package com.mmbackendassignment.mmbackendassignment.exceptions;

public class SortNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SortNotSupportedException(String sort) {
        super("Sort type '" + sort + "' not supported");
    }
}
