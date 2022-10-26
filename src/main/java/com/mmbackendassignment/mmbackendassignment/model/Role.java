package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name="roles")
public class Role {

    @Id
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role() {}

    public Role( String role ){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
