package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.service.FieldService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
                                              @Valid @RequestBody FieldInputDto dto,
                                              BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok( service.createField( addressId, dto ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editField(@PathVariable("id") long id,
                                            @Valid @RequestBody FieldInputDto dto,
                                            BindingResult br){
        Check.bindingResults( br );
        Check.hasNullable( dto );
        return ResponseEntity.ok( service.editField( id, dto ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteField(@PathVariable("id") long id){
        return ResponseEntity.ok( service.deleteField( id ) );
    }
}
