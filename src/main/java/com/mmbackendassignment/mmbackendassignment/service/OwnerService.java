package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private final OwnerRepository repo;

    public OwnerService(OwnerRepository repo) {
        this.repo = repo;
    }

    public OwnerDto getOwner( long id ){
        Owner owner = (Owner) ServiceUtil.getRepoObjectById(repo, id, "owner");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(owner);

        return ownerToDto( owner );
    }

    private OwnerDto ownerToDto( Owner owner ){
        OwnerDto dto = new OwnerDto();

        dto.profileId = owner.getProfile().getId();
        dto.addressIds = owner.getAddressIds();

        return dto;
    }
}
