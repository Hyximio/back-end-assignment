package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ProfileOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ProfilePictureDto;
import com.mmbackendassignment.mmbackendassignment.exception.CantDeleteWithDependencyException;
import com.mmbackendassignment.mmbackendassignment.exception.FileExtensionNotSupportedException;
import com.mmbackendassignment.mmbackendassignment.exception.RecordAlreadyExistException;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


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
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(user);

        long profileId = user.getProfile().getId();
        Profile profile = (Profile) ServiceUtil.getRepoObjectById(repo, profileId, "profile");

        profile = (Profile) Convert.objects(dto, profile );
        repo.save( profile );

        return "Done";
    }

    public ProfileOutputDto getProfile( String username ){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(user);

        if( user.getProfile() != null){
            return ProfileToDto( user.getProfile() );
        }else{
            throw new RecordNotFoundException("profile", username);
        }
    }

    public long createProfile(String username, ProfileInputDto dto){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(user);

        if( user.getProfile() != null ){
            throw new RecordAlreadyExistException("profile");
        }

        Profile profile = dtoToProfile( dto );

        Client client = new Client();
        client.setProfile(profile);
        clientRepo.save( client );

        profile.setUser( user );
        profile.setClient( client );

        Profile savedProfile = repo.save( profile );

        return savedProfile.getId();
    }

    public String deleteProfile( String username ){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(user);

        if ( user.getProfile() != null ) {

            if ( user.getProfile().getClient().getContracts().size() != 0 ){
                throw new CantDeleteWithDependencyException("contracts");
            }
            Check.hasDependency( user.getProfile().getOwner(), "owner" );

            clientRepo.deleteById( user.getProfile().getClient().getId() );
            return "Deleted";
        }

        throw new RecordNotFoundException( "profile", username + ".profile");
    }

    public String saveProfilePicture( String username, MultipartFile file ) throws IOException {

        /* Check if file has valid extension */
        List<String> allowedExtensions = Arrays.asList("JPG", "JPEG");

        String[] fileNameParts = file.getOriginalFilename().split("\\.");
        String extension = fileNameParts[ fileNameParts.length-1 ];
        if( allowedExtensions.contains( extension )){
            throw new FileExtensionNotSupportedException( extension );
        }

        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        JwtHandler.abortIfEntityIsNotFromSameUser(user);

        if ( user.getProfile() == null ) {
            throw new RecordNotFoundException("profile", username + ".profile");
        }

        Profile profile = user.getProfile();
        profile.setProfilePicture( file.getBytes() );

        repo.save( profile );

        return "Picture is uploaded";
    }

    public Object getProfilePicture( String username ){
        User user = (User) ServiceUtil.getRepoObjectById(userRepo, username, "username");
        JwtHandler.abortIfEntityIsNotFromSameUser(user);

        if ( user.getProfile() == null ) {
            throw new RecordNotFoundException("profile", username + ".profile");
        }

        Profile profile = user.getProfile();
        if ( profile.getProfilePicture() == null ){
            throw new RecordNotFoundException( "profile picture", username + ".profile");
        }

        ProfilePictureDto dto = new ProfilePictureDto();
        dto.profilePicture = Base64.getEncoder().encodeToString( profile.getProfilePicture() );

        return dto;
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
