package com.mmbackendassignment.mmbackendassignment.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

public class ProfileInputDto {

    @NotBlank
    public String firstName;

    @Size(min=2, max=128)
    public String lastName;

    @Email
    public String email;

    @Pattern( regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
            message = "not a valid phone number")
    public String phoneNumber;

    public Character gender;

    @Past
    public LocalDate dob;

}
