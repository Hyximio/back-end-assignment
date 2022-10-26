package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.UserInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageOutputDto;
import com.mmbackendassignment.mmbackendassignment.dto.UserOutputDto;
import com.mmbackendassignment.mmbackendassignment.model.Role;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    UserService(UserRepository repo){
        this.repo = repo;
    }

    public PageOutputDto getUsers(int page, int size ){
        PageRequest pr = PageRequest.of(page, size);
        Page<User> pageContent = repo.findAll( pr );

        PageOutputDto userContent = new PageOutputDto(
                pageContent.getNumberOfElements(),
                page,
                pageContent.getTotalPages()-1
        );

        List<User> contentList = pageContent.getContent();

        for( User u : contentList ){
            userContent.content.add( this.userToDto(u) );
        }

        return userContent;
    }

    public String createUser( UserInputDto dto ){

        Optional<User> existence = repo.findById( dto.username );

        // If existence is empty the username is not in use and safe to add
        if ( existence.isEmpty() ) {
            User user = new User( dto.username, dto.password );

            repo.save(user);

            return "Done";
        } else {
            return "Username is already in use";
        }
    }

    private UserOutputDto userToDto( User user ){
        UserOutputDto dto = new UserOutputDto(
                user.getUsername(),
                user.isEnabled()
        );

        String[] roles = new String[ user.getRoles().size() ];

        List<Role> roleList = new ArrayList<Role>( user.getRoles() );
        for( int r=0; r < user.getRoles().size(); r++ ){
            roles[r] = roleList.get(r).getRole();
        }
        dto.roles = roles;

        return dto;
    }
}
