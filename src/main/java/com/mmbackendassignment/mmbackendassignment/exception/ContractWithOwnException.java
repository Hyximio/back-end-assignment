package com.mmbackendassignment.mmbackendassignment.exception;

public class ContractWithOwnException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ContractWithOwnException() {
        super("Cannot create contract between same client & owner");
    }
}
