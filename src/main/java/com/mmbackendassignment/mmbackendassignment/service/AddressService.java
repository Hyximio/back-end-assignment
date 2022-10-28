package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AddressOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.PagableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public String createAddress(String username, AddressInputDto dto){
        User user = getUserByName( username );

        Profile profile = user.getProfile();

        /* Create owner if doesn't exist */
        if( profile.getOwner() == null ){
            Owner owner = new Owner();
            owner.setProfile( profile );
            ownerRepo.save( owner );

            profile.setOwner( owner );
            profileRepo.save( profile );
        }

        Owner owner = profile.getOwner();
        Address address = dtoToAddress( dto );
        address.setOwner( owner );

        repo.save( address );

        return "Done";
    }

    public String editAddress( long id, AddressInputDto dto){
        Optional<Address> op = repo.findById( id );

        if ( op.isPresent() ){
            Address address = (Address) Convert.objects( dto, op.get() );
            repo.save( address );
            return "Done";
        }else{
            throw new RecordNotFoundException( "address", id);
        }
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

    private User getUserByName( String username ){
        Optional<User> op = userRepo.findById( username );
        if (op.isPresent()) return op.get();
        throw new RecordNotFoundException( "username", username );
    }

}
