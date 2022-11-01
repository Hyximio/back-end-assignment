package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "fields" )
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private Float meters;
    private ArrayList<String> features;
    private Float pricePerMonth;
    private Float maxHeightMeter;

    @ManyToOne
    @JoinColumn( name = "address_id" )
    Address address;

    @OneToMany( mappedBy = "field")
    List<Contract> contracts;


    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public void setMeters(Float meters) {
        this.meters = meters;
    }

    public float getMeters(){
        return meters;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(Float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public float getMaxHeightMeter() {
        return maxHeightMeter;
    }

    public void setMaxHeightMeter(Float maxHeightMeter) {
        this.maxHeightMeter = maxHeightMeter;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public ArrayList<Long> getContractIds() {

        ArrayList<Long> contractIds = new ArrayList<>();

        for( Contract c : contracts ){
            contractIds.add( c.getId() );
        }

        return contractIds;
    }
}
