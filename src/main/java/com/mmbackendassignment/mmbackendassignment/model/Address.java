package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String street;
    private int number;
    private String city;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    Owner owner;

    @OneToMany( mappedBy = "address" )
//    @JoinColumn(name = "field_id")
    List<Field> fields;

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Field> getFields() {
        return fields;
    }

    public ArrayList<Long> getFieldIds() {

        ArrayList<Long> fieldIds = new ArrayList<>();

        for( Field f : fields ){
            fieldIds.add( f.getId() );
        }

        return fieldIds;
    }

    public void addField( Field field ){
        boolean exists = false;
        for( Field f : this.fields){
            if (f.getId() == field.getId() ){
                exists = true;
                break;
            }
        }
        if (!exists) this.fields.add( field );
    }
}
