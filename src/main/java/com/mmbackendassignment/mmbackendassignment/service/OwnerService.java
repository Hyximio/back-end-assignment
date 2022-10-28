package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository repo;

    public OwnerService(OwnerRepository repo) {
        this.repo = repo;
    }

    public Object getOwner( long id ){
        Optional<Owner> op = repo.findById( id );
        if (op.isPresent()){
            Owner owner = op.get();

            return ownerToDto( owner );
        }
        throw new RecordNotFoundException( "owner", id );
    }

    private OwnerDto ownerToDto( Owner owner ){
        OwnerDto dto = new OwnerDto();

        dto.profileId = owner.getProfile().getId();
        dto.addressIds = owner.getAddressIds();

        return dto;
    }
}
