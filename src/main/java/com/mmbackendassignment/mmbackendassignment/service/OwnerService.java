package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.exception.UsernameNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository repo;
    private final UserRepository userRepo;

    public OwnerService(OwnerRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Object getOwner( long id ){
        Optional<Owner> op = repo.findById( id );
        if (op.isPresent()){
            Owner owner = op.get();

            return ownerToDto( owner );
        }
        throw new RecordNotFoundException();
    }

    private OwnerDto ownerToDto( Owner owner ){
        OwnerDto dto = new OwnerDto();

        dto.profileId = owner.getProfile().getId();
        dto.addressIds = owner.getAddressIds();

        return dto;
    }
}
