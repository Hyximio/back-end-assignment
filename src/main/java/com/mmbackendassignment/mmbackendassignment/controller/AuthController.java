package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody AuthDto dto) {

        return ResponseEntity.ok()
                    .header( HttpHeaders.AUTHORIZATION, service.signIn(dto) )
                    .body("Token generated");
    }
}
