package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table( name = "fields" )
public class Field {

    @Id
    @GeneratedValue
    private long id;

    private String description;
    private float meters;
    private ArrayList<String> features;
    private float pricePerMonth;
    private float maxHeightMeter;

    @ManyToOne
    @JoinColumn( name = "address_id")
    Address address;

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

    public void setMeters(float meters) {
        this.meters = meters;
    }

    public float getMeters(){
        return meters;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public float getMaxHeightMeter() {
        return maxHeightMeter;
    }

    public void setMaxHeightMeter(float maxHeightMeter) {
        this.maxHeightMeter = maxHeightMeter;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
