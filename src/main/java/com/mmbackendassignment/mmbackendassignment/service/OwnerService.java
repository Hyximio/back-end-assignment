package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Contract;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository repo;

    public OwnerService(OwnerRepository repo) {
        this.repo = repo;
    }

    public Object getOwner( long id ){
        Owner owner = (Owner) ServiceUtil.getRepoObjectById(repo, id, "owner");
        return ownerToDto( owner );

        /*
        Optional<Owner> op = repo.findById( id );
        if (op.isPresent()){
            Owner owner = op.get();

            return ownerToDto( owner );
        }
        throw new RecordNotFoundException( "owner", id );

         */
    }

    private OwnerDto ownerToDto( Owner owner ){
        OwnerDto dto = new OwnerDto();

        dto.profileId = owner.getProfile().getId();
        dto.addressIds = owner.getAddressIds();

        return dto;
    }
}
