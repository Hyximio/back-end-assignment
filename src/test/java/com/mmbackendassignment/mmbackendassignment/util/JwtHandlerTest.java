package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.model.User;
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

    UserDetails ud;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("admin");
        user.addRole("ADMIN");
        UserDetails ud = (UserDetails) new MyUserDetails( user );
    }

    @Test
    @WithMockUser( username="admin", roles="ADMIN")
    void checkIfUserIsAdmin(){

        // arrange
        Authentication auth = Mockito.mock( Authentication.class );
        SecurityContext securityContext = Mockito.mock( SecurityContext.class );

        Mockito.when( securityContext.getAuthentication() ).thenReturn( auth );
        Mockito.when( auth.getPrincipal() ).thenReturn( ud );

        // act
        boolean isAdmin = JwtHandler.isAdmin();

        // assert
        assertTrue( isAdmin );
    }
}
