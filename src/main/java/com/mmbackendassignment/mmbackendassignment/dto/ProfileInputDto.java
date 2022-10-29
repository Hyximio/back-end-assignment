package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

public class ProfileInputDto {

    @Nullable
    @NotBlank
    public String firstName;

    @Nullable
    @Size(min=2, max=128)
    public String lastName;

    @Nullable
    @Email
    public String email;

    @Nullable
    @Pattern( regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
            message = "not a valid phone number")
    public String phoneNumber;

    @Nullable
    public Character gender;

    @Nullable
    @Past
    public LocalDate dob;

}
