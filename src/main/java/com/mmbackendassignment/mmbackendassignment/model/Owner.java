package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "owners" )
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne( mappedBy = "owner" )
    private Profile profile;

    @OneToMany( mappedBy = "owner" )
    List<Address> addresses;

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Long> getAddressIds() {

        ArrayList<Long> addressIds = new ArrayList<>();

        for( Address a : addresses ){
            addressIds.add( a.getId() );
        }

        return addressIds;
    }

    public void deleteAddressById( long id ){
        for( Address a : this.addresses) {
            if (a.getId() == id) {
                this.addresses.remove(id);
            }
        }
    }
    public List<Address> getAddresses() {
        return addresses;
    }
}
