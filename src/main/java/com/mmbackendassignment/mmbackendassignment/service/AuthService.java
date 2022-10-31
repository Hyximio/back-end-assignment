package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.exception.BadRequestException;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.security.MyUserDetails;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthService(UserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }


//    public AuthService(UserRepository repo, PasswordEncoder encoder) {
//        this.repo = repo;
//        this.encoder = encoder;
//    }



    public String signin(AuthDto dto ) {

        User user = (User)ServiceUtil.getRepoObjectById( repo, dto.username, "username");

        if ( encoder.matches( dto.password, user.getPassword() ) ) {

            MyUserDetails userDetails = new MyUserDetails( user );
            String token = jwtService.generateToken( userDetails );

            return "Bearer " + token;

//            return ResponseEntity.ok()
//                    .header( HttpHeaders.AUTHORIZATION, "Bearer " + token )
//                    .body("Token generated");

        }
        throw new BadRequestException();

    }
}
