package com.mmbackendassignment.mmbackendassignment.exception;


public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String type, String value) {
        super("Record " + type + " not found: " + value);
    }

    public RecordNotFoundException(String type, Long value) {
        super("Record " + type + " id not found: " + value);
    }

}
