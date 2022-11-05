package com.mmbackendassignment.mmbackendassignment.repository;

import com.mmbackendassignment.mmbackendassignment.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
