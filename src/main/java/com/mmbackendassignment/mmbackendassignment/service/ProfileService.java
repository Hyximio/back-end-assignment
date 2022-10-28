package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ProfileOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.UsernameNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository repo;
    private final UserRepository userRepo;

    ProfileService( ProfileRepository repo, UserRepository userRepo ){
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public String editProfile( String username, ProfileInputDto dto ){
        User user = getUserByName( username );
        Profile profile = user.getProfile();

        System.out.println( profile.getFirstName() );

        return "Done";
    }

    public ProfileOutputDto getProfile( String username ){
        User user = getUserByName( username );
        return ProfileToDto( user.getProfile() );
    }

    public String createProfile(String username, ProfileInputDto dto){
        User user = getUserByName( username );

        Profile profile = dtoToProfile( dto );
        user.setProfile( profile );

        repo.save( profile );
        userRepo.save( user );

        return "Done";
    }

    private ProfileOutputDto ProfileToDto(Profile profile ) {

        System.out.println( profile.getOwner() );

        ProfileOutputDto dto = (ProfileOutputDto) Convert.objects( profile, new ProfileOutputDto() );

        System.out.println( dto.ownerId );
        if( profile.getOwner() != null) {
            dto.ownerId = profile.getOwner().getId();
        }

//        dto.clientId = profile.getClient().getId();
        return dto;
    }

    private Profile dtoToProfile( ProfileInputDto dto ){
        return (Profile) Convert.objects( dto, new Profile() );
    }

    private User getUserByName(String username ){
        Optional<User> op = userRepo.findById( username );
        if (op.isPresent()) return op.get();
        throw new UsernameNotFoundException( username );
    }
}
