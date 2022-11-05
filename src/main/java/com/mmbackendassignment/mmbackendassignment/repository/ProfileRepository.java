package com.mmbackendassignment.mmbackendassignment.repository;

import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
