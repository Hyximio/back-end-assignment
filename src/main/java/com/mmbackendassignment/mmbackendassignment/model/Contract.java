package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table( name = "contracts" )
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String request;
    private boolean clientAgreement;
    private boolean ownerAgreement;

    @ManyToOne
    @JoinColumn( name = "field_id" )
    private Field field;

    @ManyToOne
    @JoinColumn( name = "client_id" )
    private Client client;


    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public boolean isClientAgreement() {
        return clientAgreement;
    }

    public void setClientAgreement(boolean clientAgreement) {
        this.clientAgreement = clientAgreement;
    }

    public boolean isOwnerAgreement() {
        return ownerAgreement;
    }

    public void setOwnerAgreement(boolean ownerAgreement) {
        this.ownerAgreement = ownerAgreement;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
