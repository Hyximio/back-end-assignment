package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.exception.BadRequestException;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.security.MyUserDetails;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    private final UserRepository repo;
//    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthService(UserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
//        this.repo = repo;
//        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public String signIn(AuthDto dto ) {

        UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(dto.username, dto.password);
        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);

            return "Bearer " + token;
        }
        catch (AuthenticationException ex) {
            throw new BadRequestException( "Didn't work yet");
        }

//        Optional op = repo.findById(dto.username);
//        if( op.isPresent() ){
//            User user = (User) op.get();
//
//            if ( encoder.matches( dto.password, user.getPassword() ) ) {
//                MyUserDetails userDetails = new MyUserDetails( user );
//                String token = jwtService.generateToken( userDetails );
//
//                return "Bearer" + token;
//            }
//        }else{
//            throw new RecordNotFoundException();
//        }


//        User user = (User)ServiceUtil.getRepoObjectById( repo, dto.username, "username");
//
//        if ( encoder.matches( dto.password, user.getPassword() ) ) {
//
//            MyUserDetails userDetails = new MyUserDetails( user );
//            String token = jwtService.generateToken( userDetails );
//
//            return "Bearer " + token;
//
//        }
//        throw new BadRequestException();
    }
}
