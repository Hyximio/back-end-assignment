package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;

@Entity
@Table( name = "clients" )
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne( mappedBy = "client" )
    private Profile profile;

    @OneToOne
    private Contract contract;


    public Long getId() {
        return id;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Profile getProfile() {
        return profile;
    }
}
