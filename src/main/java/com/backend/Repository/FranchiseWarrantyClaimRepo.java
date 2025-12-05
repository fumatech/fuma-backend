package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.Entity.FranchiseWarrantyClaim;

public interface FranchiseWarrantyClaimRepo extends JpaRepository<FranchiseWarrantyClaim, Long> {

	@Query("SELECT p FROM FranchiseWarrantyClaim p WHERE p.status = :status")
	List<FranchiseWarrantyClaim> findByStatus(@Param("status") Long status);

}
