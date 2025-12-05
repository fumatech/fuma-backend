package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.FranchiseWarrantyClaim;

public interface FranchiseWarrantyClaimService {

	FranchiseWarrantyClaim saveWarrantyClaim(FranchiseWarrantyClaim franchiseWarrantyClaim);

	List<FranchiseWarrantyClaim> getAllWarrantyClaims();

	Optional<FranchiseWarrantyClaim> getWarrantyClaimById(Long id);

	FranchiseWarrantyClaim updateWarrantyClaim(Long id, FranchiseWarrantyClaim franchiseWarrantyClaim);

	void deleteWarrantyClaim(Long id);

	FranchiseWarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus);

	List<FranchiseWarrantyClaim> getPendingOrders(); // New method for pending orders

	List<FranchiseWarrantyClaim> getAcceptedOrders(); // New method for accepted orders

	List<FranchiseWarrantyClaim> getRejectedOrders();

	List<FranchiseWarrantyClaim> getShipOrders();

}
