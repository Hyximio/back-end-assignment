package com.mmbackendassignment.mmbackendassignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.dto.UserOutputDto;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService service;

    UserOutputDto userOutput1;
    UserOutputDto userOutput2;
    UserOutputDto userOutput3;
    UserOutputDto userOutput4;
    UserOutputDto userOutput5;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup(){

//        Init MockMvc Object and build
//        Needs to be added in order to do other than GET requests, otherwise it gives an 403 response
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userOutput1 = new UserOutputDto("admin", true);
        String[] roles = new String[2];
        roles[0] = "CLIENT";
        roles[1] = "ADMIN";
        userOutput1.roles = roles;

        userOutput2 = new UserOutputDto("Mick", true);
        userOutput3 = new UserOutputDto("Lisa", true);
        userOutput4 = new UserOutputDto("Tony", false);
        userOutput5 = new UserOutputDto("Claudia", true);

    }
    @Test
    @WithMockUser( username="Mick", authorities="CLIENT")
    void getUserByUsernameAsClient() throws Exception {

        when( service.getUser("Mick") ).thenReturn( userOutput2 );

        mockMvc.perform(get("/users/Mick"))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("username").value("Mick"))
                .andExpect(jsonPath("enabled").value("true"));

    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void getAllUsersAsAdmin() throws Exception {

        PageDto pageDto = new PageDto(10, 0, 0);

        ArrayList<UserOutputDto> dtoList = new ArrayList<>();
        dtoList.add( userOutput1 );
        dtoList.add( userOutput2 );
        dtoList.add( userOutput3 );
        dtoList.add( userOutput4 );
        pageDto.content = dtoList;

        when( service.getUsers(0, 10, "username") ).thenReturn( pageDto );

        mockMvc.perform( get("/users?page=0&size=10&sort=username") )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.amount").value("10") )
                .andExpect(jsonPath("$.content[0].username").value("admin"))
                .andExpect(jsonPath("$.content[0].enabled").value("true"))
                .andExpect(jsonPath("$.content[1].username").value("Mick"))
                .andExpect(jsonPath("$.content[1].enabled").value("true"))
                .andExpect(jsonPath("$.content[2].username").value("Lisa"))
                .andExpect(jsonPath("$.content[2].enabled").value("true"))
                .andExpect(jsonPath("$.content[3].username").value("Tony"))
                .andExpect(jsonPath("$.content[3].enabled").value("false"));

    }


    @Test
    void createUser() throws Exception {

        AuthDto auth = new AuthDto();
        auth.username = "Stefan";
        auth.password = "Secret123";

        when( service.createUser( auth ) ).thenReturn( "New user created" );

        mockMvc.perform( post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content( asJsonString(auth)))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void addRole() throws Exception {

        when( service.addRole( "Mick", "ADMIN") ).thenReturn( "Done" );

        mockMvc.perform( put("/users/role/Mick/ADMIN"))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void setEnabled() throws Exception {

        when( service.addRole( "Mick", "ADMIN") ).thenReturn( "Done" );

        mockMvc.perform( put("/users/enabled/Mick/true"))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="Stefan", authorities="CLIENT")
    void setPasswordAsClient() throws Exception {

        AuthDto auth = new AuthDto();
        auth.username = "Stefan";
        auth.password = "NewPassword123";

        when( service.setPassword( auth )).thenReturn( "Done" );

        mockMvc.perform( put("/users/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content( asJsonString(auth)))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void removeRole() throws Exception {

        when( service.removeRole( "Mick", "ADMIN" )).thenReturn( "Done" );

        mockMvc.perform( delete("/users/Mick/Admin"))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void deleteUser() throws Exception {

        when( service.deleteUser( "Mick" )).thenReturn( "Done" );

        mockMvc.perform( delete("/users/Mick"))
                .andExpect( status().isOk() );
    }

    @Test
    void GetBindingResultExceptionByNotEnteringValidProperties() throws Exception {

        AuthDto auth = new AuthDto();
        auth.username = "Mick";
        auth.password = "notSafe";

        mockMvc.perform( post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(auth)))
                .andExpect( status().isNotAcceptable() );

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}