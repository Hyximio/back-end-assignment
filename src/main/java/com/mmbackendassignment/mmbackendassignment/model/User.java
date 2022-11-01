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
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne( mappedBy = "user" )
    private Profile profile;

    private boolean enabled = true;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        /* Add default role */
        this.addRole( "CLIENT" );
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

    public void addRole( String role ) {

        boolean exists = false;
        for( Role r : this.roles ){
            if (r.getRole().equals(role) ){
                exists = true;
                break;
            }
        }
        if (!exists) this.roles.add( new Role( role ) );
    }

    public void removeRole( String role ){
        this.roles.removeIf(r -> r.getRole().equals( role ) );
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
