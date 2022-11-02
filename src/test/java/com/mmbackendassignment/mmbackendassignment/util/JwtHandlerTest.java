package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.exception.EntityNotFromJwtUserException;
import com.mmbackendassignment.mmbackendassignment.model.*;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JwtHandler.class)
class JwtHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

//    UserDetails ud;
//
//    @BeforeEach
//    public void setUp() {
//        User user = new User();
//        user.setUsername("admin");
//        user.addRole("ADMIN");
//        // UserDetails ud = new MyUserDetails( user );
//        this.ud = new MyUserDetails( user );
//    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void checkIfUserIsAdmin(){

        boolean isAdmin = JwtHandler.isAdmin();
        assertTrue( isAdmin );
    }

    @Test
    @WithMockUser( username="user", authorities="Client")
    void checkIfUserIsNotAdmin(){

        boolean isAdmin = JwtHandler.isAdmin();
        assertFalse( isAdmin );
    }

    @Test
    @WithMockUser( username="Mick", authorities="Client")
    void checkIfEntityIsFromSameUser(){

        User user = new User( "Mick", "@");
        Profile profile = new Profile();
        Owner owner = new Owner();
        Address address = new Address();
        Field field = new Field();
        Client client = new Client();

        field.setAddress( address );
        address.setOwner( owner );
        owner.setProfile( profile );
        profile.setUser( user );
        client.setProfile( profile );

        assertTrue( JwtHandler.isEntityFromSameUser( user ));
        assertTrue( JwtHandler.isEntityFromSameUser( profile ));
        assertTrue( JwtHandler.isEntityFromSameUser( client ));
        assertTrue( JwtHandler.isEntityFromSameUser( owner ));
        assertTrue( JwtHandler.isEntityFromSameUser( address ));
        assertTrue( JwtHandler.isEntityFromSameUser( field ));

        User userGuest = new User( "Guest", "@");
        profile.setUser( userGuest );

        assertFalse( JwtHandler.isEntityFromSameUser( userGuest ));
        assertFalse( JwtHandler.isEntityFromSameUser( profile ));
        assertFalse( JwtHandler.isEntityFromSameUser( client ));
        assertFalse( JwtHandler.isEntityFromSameUser( owner ));
        assertFalse( JwtHandler.isEntityFromSameUser( address ));
        assertFalse( JwtHandler.isEntityFromSameUser( field ));
    }

    @Test
    @WithMockUser( username="Mick", authorities="Client")
    void abortIfEntityIsNotFromSameUser(){
        User user = new User( "Guest", "@");
        assertThrows( EntityNotFromJwtUserException.class, () -> JwtHandler.abortIfEntityIsNotFromSameUser( user ) );
    }

}
