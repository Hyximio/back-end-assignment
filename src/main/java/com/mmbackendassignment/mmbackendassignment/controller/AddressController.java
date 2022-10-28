package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.AddressInputDto;
import com.mmbackendassignment.mmbackendassignment.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAddresses( @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "100") int size,
                                           @RequestParam(defaultValue = "city") String sort) {

        return ResponseEntity.ok( service.getAddresses( page,size,sort ) );
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createAddress( @PathVariable("username") String username,
                                            @RequestBody AddressInputDto dto){

        return ResponseEntity.ok( service.createAddress(username, dto) );
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> editAddress( @PathVariable("addressId") long id,
                                          @RequestBody AddressInputDto dto){

        return ResponseEntity.ok( service.editAddress(id, dto) );
    }
}
