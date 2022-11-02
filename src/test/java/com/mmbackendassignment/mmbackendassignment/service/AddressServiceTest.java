package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AddressOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.PagableUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(JwtHandler.class)
class AddressServiceTest {

    @Mock
    AddressRepository repo;

    @Mock
    OwnerRepository ownerRepo;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    AddressService service;

    @Captor
    ArgumentCaptor<Field> fieldCaptor;

    Address address1;
    Address address2;
    Address address3;
    Owner owner1;
    Owner owner2;

    @BeforeEach
    void setup(){
        owner1 = new Owner();
        owner1.setId(1);

        owner2 = new Owner();
        owner2.setId(2);

        address1 = new Address();
        address1.setId(3);
        address1.setCity("Arnhem");
        address1.setPostalCode("6833AA");
        address1.setStreet("Graslaan");
        address1.setNumber(43);
        address1.setCountry("NL");
        address1.setOwner( owner1 );

        address2 = new Address();
        address2.setId(4);
        address2.setCity("Amsterdam");
        address2.setPostalCode("3248AA");
        address2.setStreet("Alilaan");
        address2.setNumber(14);
        address2.setCountry("NL");
        address2.setOwner( owner2 );

        address3 = new Address();
        address3.setId(5);
        address3.setCity("Utrecht");
        address3.setPostalCode("6840PQ");
        address3.setStreet("Westerweg");
        address3.setNumber(168);
        address3.setCountry("NL");
        address3.setOwner( owner2 );

        List<Address> owner2addresses = new ArrayList<>();
        owner2addresses.add(address2);
        owner2addresses.add(address3);
        owner2.setAddresses( owner2addresses );
    }

    @Test
    void getAddresses() {

        int pageIndex = 1;
        int pageSize = 10;
        String pageSort = "street";

        PageRequest pr = PagableUtil.createPageRequest( pageIndex, pageSize, pageSort, AddressOutputDto.class );

        List<Address> data = new ArrayList<>();
        data.add( address1 );
        data.add( address2 );
        Page<Address> page = new PageImpl<>(data, pr, pageSize);

        when( repo.findAll( pr )).thenReturn( page );

        PageDto dto = service.getAddresses( pageIndex, pageSize, pageSort );

        assertEquals( dto.amount, 2 );
        assertEquals( dto.currentPage, 1 );
        assertEquals( dto.totalPages, 2 );
        assertEquals( dto.content.size(), 2);

        AddressOutputDto a1 = (AddressOutputDto) dto.content.get(0);
        AddressOutputDto a2 = (AddressOutputDto) dto.content.get(1);

        assertEquals( a1.id, 3 );
        assertEquals( a2.id, 4 );
        assertEquals( a1.ownerId, 1);
        assertEquals( a2.ownerId, 2);

    }

    @Test
    @WithMockUser( username="Mick", authorities="ADMIN")
    void getAddressesByOwner() {

        List<Address> addresses = new ArrayList<>();
        addresses.add( address2 );
        addresses.add( address3 );

        when( ownerRepo.findById(2L) ).thenReturn( Optional.of(owner2) );

        List<AddressOutputDto> dtos = service.getAddressesByOwner(2);

        assertEquals( dtos.size(), 2);
    }

    @Test
    void createAddress() {
    }

    @Test
    void editAddress() {
    }

    @Test
    void deleteAddress() {
    }
}