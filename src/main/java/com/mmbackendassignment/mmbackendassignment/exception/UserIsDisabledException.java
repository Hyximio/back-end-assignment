package com.mmbackendassignment.mmbackendassignment.exception;

public class UserIsDisabledException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserIsDisabledException() {
        super("This user is disabled");
    }
}
