package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.AddressOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.exception.CantDeleteWithDependencyException;
import com.mmbackendassignment.mmbackendassignment.exception.EntityNotFromJwtUserException;
import com.mmbackendassignment.mmbackendassignment.model.*;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(JwtHandler.class)
class AddressServiceTest {

    @Mock
    AddressRepository repo;

    @Mock
    OwnerRepository ownerRepo;

    @Mock
    UserRepository userRepo;

    @Mock
    ProfileRepository profileRepo;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    AddressService service;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<Address> addressCaptor;

    Address address1;
    Address address2;
    Address address3;
    Address address4;
    Owner owner1;
    Owner owner2;
    User user;

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
        address1.setFields( new ArrayList<>() );

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

        user = new User("Mick", "@!");
        Profile profile = new Profile();
        profile.setUser( user );
        user.setProfile( profile );

        owner1.setProfile( profile );

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

//        List<Address> data = new ArrayList<>();
//        data.add( address1 );
//        data.add( address2 );
        List<Address> data = List.of( address1, address2 );
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
    @WithMockUser( username="admin", authorities="ADMIN")
    void createAddressOnUserWithNoOwnership() {

        AddressInputDto inputDto = new AddressInputDto();
        inputDto.city = "Nijmegen";
        inputDto.postalCode = "9451PQ";
        inputDto.street = "Havikstraat";
        inputDto.number = 3;
        inputDto.country = "NL";

        when( userRepo.findById("Mick")).thenReturn( Optional.of(user));
        when( repo.save( any() )).thenReturn( address1 );

        long result = service.createAddress("Mick", inputDto);

        verify(userRepo).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        ArrayList<Role> roles = new ArrayList<>(capturedUser.getRoles());

        verify(repo).save( addressCaptor.capture());
        Address capturedAddress = addressCaptor.getValue();


        assertEquals( result, 3);
        assertEquals( roles.size(), 2);
        assertEquals( roles.get(0).getRole(), "CLIENT");
        assertEquals( roles.get(1).getRole(), "OWNER");

        assertEquals( capturedAddress.getCity(), "Nijmegen");
        assertEquals( capturedAddress.getPostalCode(), "9451PQ");
        assertEquals( capturedAddress.getStreet(), "Havikstraat");
        assertEquals( capturedAddress.getNumber(), 3);
        assertEquals( capturedAddress.getCountry(), "NL");

    }

    @Test
    @WithMockUser( username="Mick", authorities="CLIENT")
    void editAddressByOwnerUser() {

        AddressInputDto inputDto = new AddressInputDto();
        inputDto.city = "Amsterdam";

        when( repo.findById( 1L )).thenReturn( Optional.of( address1 ));
        when( repo.save( any() )).thenReturn( address1 );

        service.editAddress( 1L, inputDto );

        verify(repo).save( addressCaptor.capture());
        Address capturedAddress = addressCaptor.getValue();

        assertEquals( capturedAddress.getCity(), "Amsterdam");
        assertEquals( capturedAddress.getPostalCode(), "6833AA");
        assertEquals( capturedAddress.getStreet(), "Graslaan");
        assertEquals( capturedAddress.getNumber(), 43);
        assertEquals( capturedAddress.getCountry(), "NL");
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void editAddressByAdminShouldNotBeAllowed() {

        AddressInputDto inputDto = new AddressInputDto();

        when( repo.findById( 1L )).thenReturn( Optional.of( address1 ));

        assertThrows(EntityNotFromJwtUserException.class, () -> service.editAddress( 1L, inputDto ));
    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void shouldGiveExceptionIfAddressContainFieldsWhenDeleting() {

        Address address = new Address();
        Field field = new Field();
        address.addField( field );

        when( repo.findById(1L) ).thenReturn( Optional.of(address) );

        assertThrows( CantDeleteWithDependencyException.class, () -> service.deleteAddress( 1L ) );

    }

    @Test
    @WithMockUser( username="admin", authorities="ADMIN")
    void shouldDeleteAlsoOwnerRoleWhenLastAddressIsDeleted() {

        /* Fill owner details of 'address1' */
        Owner owner = new Owner();
        List<Address> ownerAddresses = new ArrayList<>();
        ownerAddresses.add( address1 );
        owner.setAddresses( ownerAddresses );

        Profile profile = new Profile();
        profile.setOwner( owner );

        User userAsOwner = new User("Mick", "@!");
        userAsOwner.addRole("OWNER");
        userAsOwner.setProfile( profile );

        /* 2 way link needed */
        address1.setOwner( owner );
        owner.setProfile(profile);
        profile.setUser(userAsOwner);

        /* User with OWNER role and no addresses */
        Owner owner2 = new Owner();
        owner2.setAddresses( new ArrayList<>() );

        Profile profile2 = new Profile();
        profile2.setOwner( owner2 );

        User userNoAddress = new User("Mick", "@!");
        userNoAddress.addRole("OWNER");
        userNoAddress.setProfile( profile2 );

        when( repo.findById( 3L) ).thenReturn( Optional.of( address1 ) );
        when( userRepo.findById( any() ) ).thenReturn( Optional.of( userNoAddress ) );


        service.deleteAddress( 3L );

        verify(userRepo).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        ArrayList<Role> roles = new ArrayList<>(capturedUser.getRoles());


        assertEquals( roles.size(), 1);
        assertEquals( roles.get(0).getRole(), "CLIENT");

    }
}