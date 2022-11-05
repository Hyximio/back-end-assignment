package com.mmbackendassignment.mmbackendassignment.security;

import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public MyUserDetailsService(UserRepository repos) {
        this.userRepo = repos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> op = userRepo.findById(username);
        if (op.isPresent()) {
            User user = op.get();
            return new MyUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
