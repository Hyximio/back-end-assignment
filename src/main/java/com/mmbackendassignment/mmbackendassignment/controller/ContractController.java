package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.dto.ContractInputDto;
import com.mmbackendassignment.mmbackendassignment.service.ContractService;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/contracts")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContract(@PathVariable("id") long id){
        return ResponseEntity.ok( service.getContract( id ));
    }

    @PostMapping("/{clientId}/{fieldId}")
    public ResponseEntity<?> createContract(@PathVariable("clientId") long clientId,
                                            @PathVariable("fieldId") long fieldId,
                                            @Valid @RequestBody ContractInputDto dto,
                                            BindingResult br){
        Check.bindingResults( br );
        Check.hasNullable( dto );
        return ResponseEntity.ok( service.createContract( clientId, fieldId, dto ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> createContract(@PathVariable("id") long id,
                                            @Valid @RequestBody ContractInputDto dto,
                                            BindingResult br){
        Check.bindingResults( br );
        return ResponseEntity.ok( service.editContract( id, dto ));
    }

    @PutMapping("/{id}/client")
    public ResponseEntity<?> agreeClientContract(@PathVariable("id") long id,
                                                 @RequestParam( defaultValue = "false" ) boolean agree){
        return ResponseEntity.ok( service.agreeClientContract( id, agree ) );
    }

    @PutMapping("/{id}/owner")
    public ResponseEntity<?> agreeOwnerContract(@PathVariable("id") long id,
                                                @RequestParam( defaultValue = "false" ) boolean agree){
        return ResponseEntity.ok( service.agreeOwnerContract( id, agree ) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable("id") long id){
        return ResponseEntity.ok( service.deleteContract( id ));
    }
}
