package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AddressOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.*;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    private final AddressRepository repo;
    private final UserRepository userRepo;
    private final ProfileRepository profileRepo;
    private final OwnerRepository ownerRepo;

    public AddressService(AddressRepository repo, UserRepository userRepo, ProfileRepository profileRepo, OwnerRepository ownerRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.profileRepo = profileRepo;
        this.ownerRepo = ownerRepo;
    }

    public PageDto getAddresses( int page, int size, String sort ){

        PageRequest pr = PagableUtil.createPageRequest( page, size, sort, AddressOutputDto.class );

        Page<Address> pageContent = repo.findAll(pr);

        List<Address> contentList = pageContent.getContent();
        ArrayList<AddressOutputDto> contentDto = new ArrayList<>();

        for (Address a : contentList) {
            contentDto.add( this.addressToDto(a) );
        }

        return PagableUtil.createPageDto( contentDto, pageContent );

    }

    public List<AddressOutputDto> getAddressesByOwner( long ownerId ){
        Owner owner = (Owner) ServiceUtil.getRepoObjectById(ownerRepo, ownerId, "owner");

//        Authentication auth =
        if ( JwtHandler.isEntityFromSameUser( owner ) ){
            System.out.println("is owner");
        };
        List<Address> addresses = owner.getAddresses();
        ArrayList<AddressOutputDto> dtos = new ArrayList<>();

        for (Address a : addresses) {
            dtos.add( this.addressToDto(a) );
        }

        return dtos;
    }

    public long createAddress(String username, AddressInputDto dto){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");

        Profile profile = user.getProfile();

        /* Create owner if doesn't exist */
        if( profile.getOwner() == null ){
            Owner owner = new Owner();
            owner.setProfile( profile );
            ownerRepo.save( owner );

            profile.setOwner( owner );
            profileRepo.save( profile );

            user.addRole("OWNER");
            userRepo.save(user);
        }

        Owner owner = profile.getOwner();
        Address address = dtoToAddress( dto );
        address.setOwner( owner );

        Address savedAddress = repo.save( address );

        return savedAddress.getId();
    }

    public String editAddress( long id, AddressInputDto dto){

        Address address = (Address) ServiceUtil.getRepoObjectById(repo, id, "address");
        address = (Address) Convert.objects( dto, address );
        repo.save( address );
        return "Done";

    }

    public String deleteAddress( long id ){
        Address address = (Address) ServiceUtil.getRepoObjectById(repo, id, "address");
        repo.deleteById( id );

        // Remove OWNER role if no addresses are found
        String username = address.getOwner().getProfile().getUser().getUsername();
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        user.removeRole( "OWNER" );
        userRepo.save( user );

        return "Deleted";
    }

    private Address dtoToAddress( AddressInputDto dto ){
        return (Address) Convert.objects( dto, new Address());
    }

    private AddressOutputDto addressToDto(Address address ){
        AddressOutputDto dto = (AddressOutputDto) Convert.objects( address, new AddressOutputDto());
        dto.fieldIds = address.getFieldIds();
        dto.ownerId = address.getOwner().getId();
        return dto;
    }
}
