package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.VendorWarrantyClaim;

public interface VendorWarrantyClaimService {

	VendorWarrantyClaim saveWarrantyClaim(VendorWarrantyClaim vendorWarrantyClaim);

	List<VendorWarrantyClaim> getAllWarrantyClaims();

	Optional<VendorWarrantyClaim> getWarrantyClaimById(Long id);

	VendorWarrantyClaim updateWarrantyClaim(Long id, VendorWarrantyClaim vendorWarrantyClaim);

	void deleteWarrantyClaim(Long id);

	VendorWarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus);

	List<VendorWarrantyClaim> getPendingOrders(); // New method for pending orders

	List<VendorWarrantyClaim> getAcceptedOrders(); // New method for accepted orders

	List<VendorWarrantyClaim> getRejectedOrders();

	List<VendorWarrantyClaim> getShipOrders();

}
