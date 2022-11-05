package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "clients" )
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne( mappedBy = "client", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany( mappedBy = "client")
    private List<Contract> contracts;


    public Long getId() {
        return id;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public ArrayList<Long> getContractIds(){

        ArrayList<Long> contractIds = new ArrayList<>();

        for( Contract c : contracts ){
            contractIds.add( c.getId() );
        }

        return contractIds;

    }
    public Profile getProfile() {
        return profile;
    }
}
