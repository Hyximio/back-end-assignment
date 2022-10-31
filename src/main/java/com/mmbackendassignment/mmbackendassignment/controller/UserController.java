package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.security.MyUserDetails;
import com.mmbackendassignment.mmbackendassignment.service.UserService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( auth.getPrincipal().getClass() );

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String output = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Hero");
//        System.out.println(output);
        System.out.println( userDetails.getUsername() );

        return ResponseEntity.ok().body( service.getUsers( page, size, sort ) );
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser( @PathVariable("username") String username ){

        return ResponseEntity.ok().body( service.getUser( username ) );
    }

    @PostMapping("")
    public ResponseEntity<String> createUser( @Valid @RequestBody AuthDto dto,
                                              BindingResult br){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth.getPrincipal() instanceof UserDetails){
            System.out.println( ((UserDetails) auth.getPrincipal()).getUsername() );
        }
        System.out.println( "Is a stranger" );


        Check.bindingResults( br );
        return ResponseEntity.ok().body( service.createUser(dto) );
    }

    @PutMapping("/role/{username}/{role}")
    public ResponseEntity<String> addRole( @PathVariable("username") String username,
                                           @PathVariable("role") String role){

        return ResponseEntity.ok().body( service.addRole( username, role.toUpperCase() ));
    }

    @PutMapping("/enabled/{username}/{enabled}")
    public ResponseEntity<String> setEnabled( @PathVariable("username") String username,
                                              @PathVariable("enabled") boolean enabled){

        return ResponseEntity.ok().body( service.setEnabled( username, enabled ) );
    }

    @PutMapping("/password/{username}/{password}")
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
