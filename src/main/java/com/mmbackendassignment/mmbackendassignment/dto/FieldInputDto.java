package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

public class FieldInputDto {

    @Nullable
    @NotBlank
    public String description;

    @Nullable
    @Min(0)
    public Float meters;

    @Nullable
    @NotBlank
    public ArrayList<String> features;

    @Nullable
    @Min(0)
    public Float pricePerMonth;

    @Nullable
    @Min(0)
    public Float maxHeightMeter;

}
