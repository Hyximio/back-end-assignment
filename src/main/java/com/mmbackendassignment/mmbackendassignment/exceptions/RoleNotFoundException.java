package com.mmbackendassignment.mmbackendassignment.exceptions;

public class RoleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RoleNotFoundException(String role) {
        super("'" + role + "' is not a valid role");
    }
}
