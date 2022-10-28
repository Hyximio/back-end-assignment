package com.mmbackendassignment.mmbackendassignment.dto;

import java.time.LocalDate;

public class ContractOutputDto {

    public LocalDate startDate;
    public LocalDate endDate;
    public String request;
    public boolean clientAgreement;
    public boolean ownerAgreement;

    public long clientId;
    public long ownerId;
    public long fieldId;

}
