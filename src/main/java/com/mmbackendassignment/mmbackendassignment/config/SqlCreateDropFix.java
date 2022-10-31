package com.mmbackendassignment.mmbackendassignment.config;

import com.mmbackendassignment.mmbackendassignment.model.Role;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.RoleRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
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

    @Autowired
    public UserRepository userRepo;

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

        List<User> existUsers = userRepo.findAll();
        boolean adminUserExist = false;

        for( User user : existUsers ){
            if( user.getUsername().equals("admin") ) {
                adminUserExist = true;
                break;
            }
        }

        if (!adminUserExist){
            User adminUser = new User( "admin", "$2a$10$db44mu0m6Fg8edeT5WYd2OsJM/GBuG3qJ3rQ9h/v/5SqkIVfiU8ke" ); // pw:Admin123
            adminUser.addRole("CLIENT");
            adminUser.addRole("ADMIN");
            userRepo.save( adminUser );
        }
    }

//    @PostConstruct
//    public void addAdminUser(){
//
//    }
}
