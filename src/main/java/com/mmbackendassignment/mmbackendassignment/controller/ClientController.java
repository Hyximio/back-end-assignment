package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.service.ClientService;
import com.mmbackendassignment.mmbackendassignment.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getOwner(@PathVariable("clientId") long id ){
        return ResponseEntity.ok( service.getClient( id ));
    }

}
