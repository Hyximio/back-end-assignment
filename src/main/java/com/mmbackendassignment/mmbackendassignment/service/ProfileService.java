package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ProfileOutputDto;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;


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
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        long profileId = user.getProfile().getId();
        Profile profile = (Profile) ServiceUtil.getRepoObjectById(repo, profileId, "profile");

        profile = (Profile) Convert.objects(dto, profile );
        repo.save( profile );

        return "Done";
    }

    public ProfileOutputDto getProfile( String username ){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        return ProfileToDto( user.getProfile() );
    }

    public long createProfile(String username, ProfileInputDto dto){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        Profile profile = dtoToProfile( dto );

        Client client = new Client();
        clientRepo.save( client );

        profile.setUser( user );
        profile.setClient( client );
        Profile savedProfile = repo.save( profile );

        return savedProfile.getId();
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

}
