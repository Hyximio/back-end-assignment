package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.service.UserService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    public UserController( UserService service ){
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers( @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "100") int size,
                                       @RequestParam(defaultValue = "username") String sort){

        return ResponseEntity.ok().body( service.getUsers( page, size, sort ) );
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser( @PathVariable("username") String username ){

        return ResponseEntity.ok().body( service.getUser( username ) );
    }

    @PostMapping("")
    public ResponseEntity<String> createUser( @Valid @RequestBody AuthDto dto,
                                              BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok().body( service.createUser(dto) );
    }

    @PutMapping("/{username}/role/{role}")
    public ResponseEntity<String> addRole( @PathVariable("username") String username,
                                           @PathVariable("role") String role){

        return ResponseEntity.ok().body( service.addRole( username, role.toUpperCase() ));
    }

    @PutMapping("/{username}/enabled/{enabled}")
    public ResponseEntity<String> setEnabled( @PathVariable("username") String username,
                                              @PathVariable("enabled") boolean enabled){

        return ResponseEntity.ok().body( service.setEnabled( username, enabled ) );
    }

    @PostMapping("/{username}/password/{password}")
    public ResponseEntity<String> setUserSettings( @PathVariable("username") String username,
                                                   @PathVariable("password") String password){

        return ResponseEntity.ok().body(service.setPassword(username, password));
    }

    @DeleteMapping("/{username}/{role}")
    public ResponseEntity<String> removeRole( @PathVariable("username") String username,
                                              @PathVariable("role") String role) {

        return ResponseEntity.ok().body( service.removeRole( username, role.toUpperCase() ));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser( @PathVariable("username") String username) {

        return ResponseEntity.ok().body( service.deleteUser( username ) );
    }

}
