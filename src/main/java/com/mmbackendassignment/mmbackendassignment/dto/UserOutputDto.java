package com.mmbackendassignment.mmbackendassignment.dto;

public class UserOutputDto {

    public String username;
    public boolean enabled;
    public String[] roles;

    public UserOutputDto(String username, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }
}
