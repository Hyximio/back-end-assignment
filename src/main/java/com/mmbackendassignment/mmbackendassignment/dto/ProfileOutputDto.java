package com.mmbackendassignment.mmbackendassignment.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ProfileOutputDto {


    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Character gender;
    public LocalDate dob;

    public Long ownerId;
    public Long clientId;
}
