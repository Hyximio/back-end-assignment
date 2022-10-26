package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.UserInputDto;
import com.mmbackendassignment.mmbackendassignment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    public UserController( UserService service ){
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers( @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "100") int size ){

        return ResponseEntity.ok().body( service.getUsers( page, size ) );
    }

    @PostMapping("")
    public ResponseEntity<String> createUser( @RequestBody UserInputDto dto ){
        return ResponseEntity.ok().body( service.createUser(dto) );
    }
}
