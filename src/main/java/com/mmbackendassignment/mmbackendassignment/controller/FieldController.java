package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.service.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fields")
public class FieldController {

    private final FieldService service;

    public FieldController(FieldService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getField( @PathVariable("id") long id){
        return ResponseEntity.ok( service.getField( id ));
    }

    @PostMapping("/{addressId}")
    public ResponseEntity<Object> createField(@PathVariable("addressId") long addressId,
                                              @RequestBody FieldInputDto dto){
        return ResponseEntity.ok( service.createField( addressId, dto ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editField(@PathVariable("id") long id,
                                              @RequestBody FieldInputDto dto){
        return ResponseEntity.ok( service.editField( id, dto ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteField(@PathVariable("id") long id){
        return ResponseEntity.ok( service.deleteField( id ) );
    }
}
