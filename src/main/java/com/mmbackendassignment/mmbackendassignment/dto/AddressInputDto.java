package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AddressInputDto {

    @Nullable
    @NotBlank
    public String street;

    @Nullable
    @Min(1)
    public int number;

    @Nullable
    @NotBlank
    public String city;

    @Nullable
    @Pattern( regexp = "^[1-9][0-9]{3} ?(?!sa|sd|ss|SA|SD|SS)[A-Za-z]{2}$", message = "postalCode: Not valid" )
    public String postalCode;

    @Nullable
    @NotBlank
    public String country;
}
