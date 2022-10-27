package com.mmbackendassignment.mmbackendassignment.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class AuthDto {

    @Length(min=4, max=128)
    public String username;

    @Pattern( regexp = "\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"",
            message = "Password is not safe enough")
    public String password;

}
