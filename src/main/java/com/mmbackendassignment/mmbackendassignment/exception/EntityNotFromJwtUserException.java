package com.mmbackendassignment.mmbackendassignment.exception;

public class EntityNotFromJwtUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFromJwtUserException() {
        super("Entity not possessed by current user");
    }
}
