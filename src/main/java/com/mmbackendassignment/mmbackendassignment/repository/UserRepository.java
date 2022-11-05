package com.mmbackendassignment.mmbackendassignment.repository;

import com.mmbackendassignment.mmbackendassignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
