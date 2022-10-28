package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.service.ProfileService;
import com.mmbackendassignment.mmbackendassignment.util.BindingResultUtil;
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
    public ResponseEntity<String> createProfile(@PathVariable("username") String username,
                                                @Valid @RequestBody ProfileInputDto dto,
                                                BindingResult br){
        BindingResultUtil.check( br );
        return ResponseEntity.ok( service.createProfile( username, dto) );
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable("username") String username){
        return ResponseEntity.ok( service.getProfile( username ) );
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile( @PathVariable("username") String username,
                                          @RequestBody ProfileInputDto dto,
                                          BindingResult br){
        BindingResultUtil.check( br );
        return ResponseEntity.ok( service.editProfile( username, dto) );
    }


}
