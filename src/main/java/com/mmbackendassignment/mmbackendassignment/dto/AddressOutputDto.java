package com.mmbackendassignment.mmbackendassignment.dto;

import java.util.ArrayList;

public class AddressOutputDto {

    public long id;

    public String street;
    public int number;
    public String city;
    public String postalCode;
    public String country;

    public ArrayList<Long> fieldIds;
    public long ownerId;
}
