package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(JwtHandler.class)
class OwnerServiceTest {

    @Mock
    OwnerRepository repo;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    OwnerService service;

    Owner owner;
    Profile profile;
    Address address1;
    Address address2;

    @BeforeEach
    void setup(){
        profile = new Profile();
        profile.setId(1);

        address1 = new Address();
        address2 = new Address();
        address1.setId(2);
        address2.setId(3);

        List<Address> addresses = new ArrayList<>();
        addresses.add( address1 );
        addresses.add( address2 );

        owner = new Owner();
        owner.setId(4);
        owner.setProfile( profile );
        owner.setAddresses( addresses );
    }

    @Test
    @WithMockUser( username="Mick", authorities="ADMIN")
    void getOwner() {

        when( repo.findById(1L) ).thenReturn( Optional.ofNullable(owner) );

        OwnerDto result = service.getOwner( 1L );

        List<Long> addressIds = new ArrayList<>();
        addressIds.add( 2L );
        addressIds.add( 3L );

        assertEquals( result.profileId, 1);
        assertEquals( result.addressIds, addressIds);
    }
}