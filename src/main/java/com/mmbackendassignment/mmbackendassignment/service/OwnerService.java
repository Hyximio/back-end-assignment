package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.exception.UsernameNotFoundException;
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

    public Object getOwner(String username ){
        User user = getUserByName( username );

        Profile profile = user.getProfile();

        Owner owner = profile.getOwner();
        if ( profile.getOwner() != null ){

        }

        return owner;
    }

    private User getUserByName(String username ){
        Optional<User> op = userRepo.findById( username );
        if (op.isPresent()) return op.get();
        throw new UsernameNotFoundException( username );
    }

    private OwnerDto ownerToDto(Owner owner ){
        return (OwnerDto) Convert.objects( owner, new OwnerDto() );
    }
}
