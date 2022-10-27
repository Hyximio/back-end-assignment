package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "owners" )
public class Owner {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany
    @JoinColumn(name = "address_id")
    List<Address> addresses;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
