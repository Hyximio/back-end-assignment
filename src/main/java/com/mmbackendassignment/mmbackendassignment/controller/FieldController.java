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

    @GetMapping("/{fieldId}")
    public ResponseEntity<?> getField( @PathVariable("fieldId") long fieldId){
        return ResponseEntity.ok( service.getField( fieldId ));
    }
    @PostMapping("/{addressId}")
    public ResponseEntity<Object> createField(@PathVariable("addressId") long addressId,
                                              @RequestBody FieldInputDto dto){
        return ResponseEntity.ok( service.createField( addressId, dto ));
    }

    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Object> deleteField(@PathVariable("fieldId") long fieldId){
        return ResponseEntity.ok( service.deleteField( fieldId ) );
    }
}
