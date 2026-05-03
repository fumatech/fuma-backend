package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {

	Optional<Warehouse> findByUsername(String username);

	Optional<Warehouse> findByUsernameAndPasswordAndIsActiveTrue(String username, String password);

	Optional<Warehouse> findByIsDefaultTrue();
}

