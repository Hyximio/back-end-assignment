package com.mmbackendassignment.mmbackendassignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.AddressOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.service.AddressService;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    AddressService service;

    AddressOutputDto addressOutput1;
    AddressOutputDto addressOutput2;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup(){

//        Init MockMvc Object and build
//        Needed to do POST requests, otherwise it gave an 403 response
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        addressOutput1 = new AddressOutputDto();
        addressOutput1.city = "Arnhem";
        addressOutput1.postalCode = "6833AA";
        addressOutput1.street = "Graslaan";
        addressOutput1.number = 43;
        addressOutput1.country = "NL";

        addressOutput2 = new AddressOutputDto();
        addressOutput2.city = "Breda";
        addressOutput2.postalCode = "4933AA";
        addressOutput2.street = "Wegisweg";
        addressOutput2.number = 66;
        addressOutput2.country = "NL";

    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void getAddresses() throws Exception {

        PageDto pageDto = new PageDto(10, 1, 1);

        ArrayList<AddressOutputDto> dtoList = new ArrayList<>();
        dtoList.add( addressOutput1 );
        dtoList.add( addressOutput2 );
        pageDto.content = dtoList;

        when( service.getAddresses(0, 10, "street") ).thenReturn( pageDto );

        mockMvc.perform( get("/addresses?page=1&size=10&sort=street") )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.amount").value("10") )
                .andExpect(jsonPath("$.content[0].city").value("Arnhem"))
                .andExpect(jsonPath("$.content[0].postalCode").value("6833AA"))
                .andExpect(jsonPath("$.content[0].street").value("Graslaan"))
                .andExpect(jsonPath("$.content[0].number").value("43"))
                .andExpect(jsonPath("$.content[0].country").value("NL"))
                .andExpect(jsonPath("$.content[1].city").value("Breda"))
                .andExpect(jsonPath("$.content[1].postalCode").value("4933AA"))
                .andExpect(jsonPath("$.content[1].street").value("Wegisweg"))
                .andExpect(jsonPath("$.content[1].number").value("66"))
                .andExpect(jsonPath("$.content[1].country").value("NL"));

    }

    @Test
    @WithMockUser( username="client", authorities="CLIENT")
    void getOwnerAddresses() throws Exception {

        when( service.getAddressesByOwner( 1L ) ).thenReturn( List.of( addressOutput1, addressOutput2) );

        mockMvc.perform( get("/addresses/1") )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$[0].city").value("Arnhem"))
                .andExpect(jsonPath("$[0].postalCode").value("6833AA"))
                .andExpect(jsonPath("$[0].street").value("Graslaan"))
                .andExpect(jsonPath("$[0].number").value("43"))
                .andExpect(jsonPath("$[0].country").value("NL"))
                .andExpect(jsonPath("$[1].city").value("Breda"))
                .andExpect(jsonPath("$[1].postalCode").value("4933AA"))
                .andExpect(jsonPath("$[1].street").value("Wegisweg"))
                .andExpect(jsonPath("$[1].number").value("66"))
                .andExpect(jsonPath("$[1].country").value("NL"));
    }

    @Test
    @WithMockUser( username="owner", authorities="OWNER")
    void createAddress() throws Exception {

        AddressInputDto inputDto = new AddressInputDto();
        inputDto.city = "Nijmegen";
        inputDto.postalCode = "9451PQ";
        inputDto.street = "Havikstraat";
        inputDto.number = 3;
        inputDto.country = "NL";

        when( service.createAddress( "Mick", inputDto ) ).thenReturn( 2L );

        mockMvc.perform( post("/addresses/Mick")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(inputDto)))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="owner", authorities="OWNER")
    void editsAddress() throws Exception {

        AddressInputDto inputDto = new AddressInputDto();
        inputDto.city = "Nijmegen";

        when( service.editAddress( 1, inputDto ) ).thenReturn( "Done" );

        mockMvc.perform( put("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(inputDto)))
                .andExpect( status().isOk() );
    }

    @Test
    @WithMockUser( username="owner", authorities="OWNER")
    void deleteAddress() throws Exception {

        when( service.deleteAddress( 1 )).thenReturn( "Done" );

        mockMvc.perform( delete("/addresses/1"))
                .andExpect( status().isOk() );

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}