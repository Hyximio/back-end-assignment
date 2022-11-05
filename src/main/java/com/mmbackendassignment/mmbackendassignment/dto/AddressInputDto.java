package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddressInputDto {

    @Nullable
    @Size(min = 1)
    public String street;

    @Nullable
    @Min(1)
    public Integer number;

    @Nullable
    @Size(min = 1)
    public String city;

    @Nullable
    @Pattern( regexp = "^[1-9][0-9]{3} ?(?!sa|sd|ss|SA|SD|SS)[A-Za-z]{2}$", message = "Not valid" )
    public String postalCode;

    @Nullable
    @Size(min = 1)
    public String country;
}
