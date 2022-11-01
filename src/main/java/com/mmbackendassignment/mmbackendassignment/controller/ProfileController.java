package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.ProfileInputDto;
import com.mmbackendassignment.mmbackendassignment.profilePicture.FileUploadResponse;
import com.mmbackendassignment.mmbackendassignment.service.ProfileService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getProfile(username));
    }

    @PostMapping("/{username}")
    public ResponseEntity<Long> createProfile(@PathVariable("username") String username,
                                              @Valid @RequestBody ProfileInputDto dto,
                                              BindingResult br) {
        Check.bindingResults(br);
        Check.hasNullable(dto);
        return ResponseEntity.ok(service.createProfile(username, dto));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile( @PathVariable("username") String username,
                                          @Valid @RequestBody ProfileInputDto dto,
                                          BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok( service.editProfile( username, dto) );
    }

    @PutMapping("/upload/{username}")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file,
                                           @PathVariable String username) throws IOException {
        return ResponseEntity.ok(service.saveProfilePicture( username, file ));
    }

    @GetMapping("/download/{username}")
    public ResponseEntity<?> downloadPicture(@PathVariable String username){
        return ResponseEntity.ok(service.getProfilePicture( username ));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteProfile( @PathVariable("username") String username){

        return ResponseEntity.ok( service.deleteProfile( username) );
    }
}
