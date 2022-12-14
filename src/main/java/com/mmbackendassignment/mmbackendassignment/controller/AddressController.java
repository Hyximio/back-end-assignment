package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.service.AddressService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getAddresses( @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "100") int size,
                                           @RequestParam(defaultValue = "city") String sort) {

        return ResponseEntity.ok( service.getAddresses( page-1, size, sort ) );
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<?> getOwnerAddresses( @PathVariable("ownerId") long ownerId) {
        return ResponseEntity.ok( service.getAddressesByOwner( ownerId ) );
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createAddress( @PathVariable("username") String username,
                                            @Valid @RequestBody AddressInputDto dto,
                                            BindingResult br){
        Check.bindingResults( br );
        Check.hasNullable( dto );
        return ResponseEntity.ok( service.createAddress(username, dto) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editsAddress( @PathVariable("id") long id,
                                          @Valid @RequestBody AddressInputDto dto,
                                          BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok( service.editAddress(id, dto) );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress( @PathVariable("id") long id ){
        return ResponseEntity.ok( service.deleteAddress( id ));
    }
}
