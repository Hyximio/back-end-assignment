package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class FieldInputDto {

    @Nullable
    @Size(min = 1)
    public String description;

    @Nullable
    @Min(1)
    public Float meters;

    @Nullable
    @Size(min = 1, message = "features: give at least one feature")
    public ArrayList<String> features;

    @Nullable
    @Min(0)
    public Float pricePerMonth;

    @Nullable
    @Min(0)
    public Float maxHeightMeter;

}
