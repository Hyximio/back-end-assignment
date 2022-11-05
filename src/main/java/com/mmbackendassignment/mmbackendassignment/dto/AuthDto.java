package com.mmbackendassignment.mmbackendassignment.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class AuthDto {

    @Pattern( regexp = "^[A-Za-z\\d._-]+", message = "only alphabet characters, digits and (._-) allowed ")
    @Length(min=4, max=40)
    public String username;

    @Pattern( regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "not safe enough (need char, capital and digit with min length of 8)")
    public String password;

}
