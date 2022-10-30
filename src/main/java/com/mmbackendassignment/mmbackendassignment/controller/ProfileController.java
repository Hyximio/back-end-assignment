package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.service.ProfileService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController( ProfileService service ){
        this.service = service;
    }

    @PostMapping("/{username}")
    public ResponseEntity<Long> createProfile(@PathVariable("username") String username,
                                                @Valid @RequestBody ProfileInputDto dto,
                                                BindingResult br){
        Check.bindingResults( br );
        Check.hasNullable( dto );
        return ResponseEntity.ok( service.createProfile( username, dto) );
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable("username") String username){
        return ResponseEntity.ok( service.getProfile( username ) );
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile( @PathVariable("username") String username,
                                          @Valid @RequestBody ProfileInputDto dto,
                                          BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok( service.editProfile( username, dto) );
    }

}
