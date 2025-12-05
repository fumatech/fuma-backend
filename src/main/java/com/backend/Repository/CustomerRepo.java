package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

	@Query("SELECT v.franchiseName FROM Customer v WHERE v.email = :email")
	Optional<String> findFirmNameByEmail(String email);

	boolean existsByFranchiseId(String franchiseId); // Add this method

	List<Customer> findByIsActiveTrue();

	List<Customer> findByIsActiveFalse();

	Optional<Customer> findByFranchiseId(String franchiseId);

}
