package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;

@Entity
@Table( name = "clients" )
public class Client {

    @Id
    @GeneratedValue
    private Long id;

}
