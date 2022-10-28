package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ProfileOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository repo;
    private final UserRepository userRepo;
    private final ClientRepository clientRepo;

    public ProfileService(ProfileRepository repo, UserRepository userRepo, ClientRepository clientRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.clientRepo = clientRepo;
    }

    public String editProfile(String username, ProfileInputDto dto ){
        User user = getUserByName( username );

        Optional<Profile> op = repo.findById( user.getProfile().getId() );
        if ( op.isPresent() ) {

            Profile profile = (Profile) Convert.objects(dto, op.get() );

            /* Fix to also mirror the 'Character' value */
            if( dto.gender != null )
                profile.setGender( dto.gender );

            repo.save( profile );

        }

        return "Done";
    }

    public ProfileOutputDto getProfile( String username ){
        User user = getUserByName( username );
        return ProfileToDto( user.getProfile() );
    }

    public String createProfile(String username, ProfileInputDto dto){
        User user = getUserByName( username );
        Profile profile = dtoToProfile( dto );

        Client client = new Client();
        clientRepo.save( client );

        profile.setUser( user );
        profile.setClient( client );
        repo.save( profile );

        return "Done";
    }

    private ProfileOutputDto ProfileToDto(Profile profile ) {

        ProfileOutputDto dto = (ProfileOutputDto) Convert.objects( profile, new ProfileOutputDto() );

        if( profile.getOwner() != null) {
            dto.ownerId = profile.getOwner().getId();
        }

        if( profile.getClient() != null) {
            dto.clientId = profile.getClient().getId();
        }

        return dto;
    }

    private Profile dtoToProfile( ProfileInputDto dto ){
        Profile profile = (Profile) Convert.objects( dto, new Profile());
        return profile;
    }

    private User getUserByName(String username ){
        Optional<User> op = userRepo.findById( username );
        if (op.isPresent()) return op.get();
        throw new RecordNotFoundException( "username", username );
    }
}
