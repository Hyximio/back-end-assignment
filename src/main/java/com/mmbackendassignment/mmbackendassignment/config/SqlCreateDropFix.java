package com.mmbackendassignment.mmbackendassignment.config;

import com.mmbackendassignment.mmbackendassignment.model.Role;
import com.mmbackendassignment.mmbackendassignment.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class SqlCreateDropFix {

    @Autowired
    public RoleRepository roleRepo;

    @Value("${spring.jpa.hibernate.dll-auto}")
    private String sqlStartupType;

    @PostConstruct
    public void addRoles(){
        if( this.sqlStartupType.equals( "create-drop") ){
            System.out.println("Create-drop fix executed");
        }

        List<Role> existRoles = roleRepo.findAll();

        List<String> roles = Arrays.asList( "CLIENT", "OWNER", "ADMIN" );

        for( String r : roles ){
            if ( !existRoles.contains(r) ){
                roleRepo.save( new Role(r) );
            }
        }
    }
}
