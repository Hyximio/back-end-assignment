package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private char gender;
    private Date dob;

//    private Client client;
//    private Owner owner;

    @OneToOne( mappedBy = "profile")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
