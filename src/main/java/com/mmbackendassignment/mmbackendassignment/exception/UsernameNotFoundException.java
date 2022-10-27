package com.mmbackendassignment.mmbackendassignment.exception;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Cannot find username '" + username + "'");
    }

}
