package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
//    private final AuthenticationManager authManager;

//    public AuthService(UserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager) {
//        this.repo = repo;
//        this.encoder = encoder;
//        this.authManager = authManager;
//    }


    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public String signup(AuthDto dto ) {

        // Check if username is already exist
        Optional<User> existence = repo.findById( dto.username );

        // If existence is empty the username is not in use and safe to add
        if ( existence.isEmpty() ) {
            User user = new User( dto.username, encoder.encode( dto.password ) );

            repo.save(user);
            return "Done";
        } else {
            return "Username is already in use";
        }
    }

    public String signin( AuthDto dto ) {

        User user = (User)ServiceUtil.getRepoObjectById( repo, dto.username, "username");

        if ( encoder.matches( dto.password, user.getPassword() ) ) {
            return "Sign in as " + dto.username;
        }
        return "Password does not match";

    }
}
