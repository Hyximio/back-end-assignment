package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController( ProfileService service ){
        this.service = service;
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> createProfile( @PathVariable("username") String username,
                                            @RequestBody ProfileInputDto dto){

        return ResponseEntity.ok( service.createProfile( username, dto) );
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile( @PathVariable("username") String username,
                                          @RequestBody ProfileInputDto dto){

        return ResponseEntity.ok( service.editProfile( username, dto) );
    }

//    @PutMapping("/{username}/picture")

}
