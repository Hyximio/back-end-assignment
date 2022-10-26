package com.mmbackendassignment.mmbackendassignment.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<Role>();

    private boolean enabled = true;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        Collection<Role> defaultRoles = new ArrayList<Role>();
        defaultRoles.add( new Role( "CLIENT" ) );
        this.setRoles( defaultRoles );
//        this.roles.add( new Role( "CLIENT" ) );
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
