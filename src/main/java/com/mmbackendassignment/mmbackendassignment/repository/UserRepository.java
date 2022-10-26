package com.mmbackendassignment.mmbackendassignment.repository;

import com.mmbackendassignment.mmbackendassignment.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {


}
