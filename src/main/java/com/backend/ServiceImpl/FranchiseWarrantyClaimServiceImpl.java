package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.FranchiseWarrantyClaim;
import com.backend.Repository.FranchiseWarrantyClaimRepo;
import com.backend.Service.FranchiseWarrantyClaimService;

@Service
public class FranchiseWarrantyClaimServiceImpl implements FranchiseWarrantyClaimService {

	@Autowired
	private FranchiseWarrantyClaimRepo franchiseWarrantyClaimRepo;

	@Override
	public FranchiseWarrantyClaim saveWarrantyClaim(FranchiseWarrantyClaim franchiseWarrantyClaim) {
		if (franchiseWarrantyClaim.getFranchiseWarrantyClaimItems() != null) {
			franchiseWarrantyClaim.getFranchiseWarrantyClaimItems()
					.forEach(item -> item.setFranchiseWarrantyClaim(franchiseWarrantyClaim));
		}
		return franchiseWarrantyClaimRepo.save(franchiseWarrantyClaim);
	}

	@Override
	public List<FranchiseWarrantyClaim> getAllWarrantyClaims() {
		return franchiseWarrantyClaimRepo.findAll();
	}

	@Override
	public Optional<FranchiseWarrantyClaim> getWarrantyClaimById(Long id) {
		return franchiseWarrantyClaimRepo.findById(id);
	}

	@Override
	public FranchiseWarrantyClaim updateWarrantyClaim(Long id, FranchiseWarrantyClaim franchiseWarrantyClaim) {
		Optional<FranchiseWarrantyClaim> existingClaim = franchiseWarrantyClaimRepo.findById(id);

		if (existingClaim.isPresent()) {
			FranchiseWarrantyClaim updatedClaim = existingClaim.get();
			updatedClaim.setVendor(franchiseWarrantyClaim.getVendor());
			updatedClaim.setFranchise(franchiseWarrantyClaim.getFranchise());
			updatedClaim.setReferenceNumber(franchiseWarrantyClaim.getReferenceNumber());
			updatedClaim.setDate(franchiseWarrantyClaim.getDate());
			updatedClaim.setStatus(franchiseWarrantyClaim.getStatus());
			updatedClaim.setTotalAmount(franchiseWarrantyClaim.getTotalAmount());
			updatedClaim.setUpdatedTotalAmount(franchiseWarrantyClaim.getUpdatedTotalAmount());
			updatedClaim.setTotalUnits(franchiseWarrantyClaim.getTotalUnits());
			updatedClaim.setUpdatedTotalUnits(franchiseWarrantyClaim.getUpdatedTotalUnits());
			updatedClaim.setReason(franchiseWarrantyClaim.getReason());

			if (franchiseWarrantyClaim.getFranchiseWarrantyClaimItems() != null) {
				franchiseWarrantyClaim.getFranchiseWarrantyClaimItems()
						.forEach(item -> item.setFranchiseWarrantyClaim(updatedClaim));
				updatedClaim.setFranchiseWarrantyClaimItems(franchiseWarrantyClaim.getFranchiseWarrantyClaimItems());
			}

			if (franchiseWarrantyClaim.getStockTransaction() != null) {
				franchiseWarrantyClaim.getStockTransaction().forEach(tx -> tx.setFranchiseWarrantyClaim(updatedClaim));
				updatedClaim.setStockTransaction(franchiseWarrantyClaim.getStockTransaction());
			}

			return franchiseWarrantyClaimRepo.save(updatedClaim);
		} else {
			throw new RuntimeException("Franchise Warranty Claim not found with ID: " + id);
		}
	}

	@Override
	public void deleteWarrantyClaim(Long id) {
		if (franchiseWarrantyClaimRepo.existsById(id)) {
			franchiseWarrantyClaimRepo.deleteById(id);
		} else {
			throw new RuntimeException("Franchise Warranty Claim not found with ID: " + id);
		}
	}

	@Override
	public FranchiseWarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus) {
		Optional<FranchiseWarrantyClaim> existingClaim = franchiseWarrantyClaimRepo.findById(id);

		if (existingClaim.isPresent()) {
			FranchiseWarrantyClaim claim = existingClaim.get();
			claim.setStatus(newStatus);
			return franchiseWarrantyClaimRepo.save(claim);
		} else {
			throw new RuntimeException("Franchise Warranty Claim not found with ID: " + id);
		}
	}

	@Override
	public List<FranchiseWarrantyClaim> getPendingOrders() {
		return franchiseWarrantyClaimRepo.findByStatus(0L);
	}

	@Override
	public List<FranchiseWarrantyClaim> getAcceptedOrders() {
		return franchiseWarrantyClaimRepo.findByStatus(1L);
	}

	@Override
	public List<FranchiseWarrantyClaim> getRejectedOrders() {
		return franchiseWarrantyClaimRepo.findByStatus(2L);
	}

	@Override
	public List<FranchiseWarrantyClaim> getShipOrders() {
		return franchiseWarrantyClaimRepo.findByStatus(3L);
	}
}
