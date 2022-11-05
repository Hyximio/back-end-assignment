package com.mmbackendassignment.mmbackendassignment.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ContractInputDto {

    @Nullable
    @FutureOrPresent
    public LocalDate startDate;

    @Nullable
    @Future
    public LocalDate endDate;

    @Nullable
    @NotBlank
    public String request;

}
