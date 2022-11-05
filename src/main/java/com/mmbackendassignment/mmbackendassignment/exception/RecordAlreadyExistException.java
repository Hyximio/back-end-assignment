package com.mmbackendassignment.mmbackendassignment.exception;

public class RecordAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistException(String record) {
        super("Record '" + record + "' already populated, use a PUT request or delete populated record first");
    }
}
