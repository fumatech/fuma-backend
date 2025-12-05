package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.VendorWarrantyClaim;
import com.backend.Repository.VendorWarrantyClaimRepo;
import com.backend.Service.VendorWarrantyClaimService;

@Service
public class VendorWarrantyClaimServiceImpl implements VendorWarrantyClaimService {
	
   @Autowired
   private VendorWarrantyClaimRepo vendorWarrantyClaimRepo; 

	@Override
	public VendorWarrantyClaim saveWarrantyClaim(VendorWarrantyClaim vendorWarrantyClaim) {
	        if (vendorWarrantyClaim.getVendorWarrantyClaimItems() != null) {
	        	vendorWarrantyClaim.getVendorWarrantyClaimItems().forEach(item -> item.setVendorWarrantyClaim(vendorWarrantyClaim));
	        }
	     
	        return vendorWarrantyClaimRepo.save(vendorWarrantyClaim);

	}

	@Override
	public List<VendorWarrantyClaim> getAllWarrantyClaims() {

        return vendorWarrantyClaimRepo.findAll();

	}

	@Override
	public Optional<VendorWarrantyClaim> getWarrantyClaimById(Long id) {

        return vendorWarrantyClaimRepo.findById(id);

	}

	@Override
	public VendorWarrantyClaim updateWarrantyClaim(Long id, VendorWarrantyClaim vendorWarrantyClaim) {

	      Optional<VendorWarrantyClaim> existingWarrantyClaim = vendorWarrantyClaimRepo.findById(id);

	        if (existingWarrantyClaim.isPresent()) {
	        	VendorWarrantyClaim updatedWarrantyClaim = existingWarrantyClaim.get();
	            updatedWarrantyClaim.setVendor(vendorWarrantyClaim.getVendor());
	            updatedWarrantyClaim.setReferenceNumber(vendorWarrantyClaim.getReferenceNumber());
	            updatedWarrantyClaim.setDate(vendorWarrantyClaim.getDate());
	            updatedWarrantyClaim.setStatus(vendorWarrantyClaim.getStatus());   
	            updatedWarrantyClaim.setTotalAmount(vendorWarrantyClaim.getTotalAmount());
	            updatedWarrantyClaim.setUpdatedTotalAmount(vendorWarrantyClaim.getUpdatedTotalAmount());
	            updatedWarrantyClaim.setUpdatedTotalUnits(vendorWarrantyClaim.getUpdatedTotalUnits());
	            updatedWarrantyClaim.setTotalUnits(vendorWarrantyClaim.getTotalUnits());
	            updatedWarrantyClaim.setReason(vendorWarrantyClaim.getReason());

	             if (vendorWarrantyClaim.getVendorWarrantyClaimItems() != null) {
	            	 vendorWarrantyClaim.getVendorWarrantyClaimItems().forEach(item -> item.setVendorWarrantyClaim(updatedWarrantyClaim));
	                updatedWarrantyClaim.setVendorWarrantyClaimItems(vendorWarrantyClaim.getVendorWarrantyClaimItems());
	            }

	            // Update Warranty Transactions (matching entity)
	            if (vendorWarrantyClaim.getStockTransaction() != null) {
	                vendorWarrantyClaim.getStockTransaction().forEach(item -> item.setVendorWarrantyClaim(updatedWarrantyClaim));
	                updatedWarrantyClaim.setStockTransaction(vendorWarrantyClaim.getStockTransaction());
	            }

	            return vendorWarrantyClaimRepo.save(updatedWarrantyClaim);
	        } else {
	            throw new RuntimeException("Warranty Claim not found with ID: " + id);
	        }
		
	}

	@Override
	public void deleteWarrantyClaim(Long id) {
		  if (vendorWarrantyClaimRepo.existsById(id)) {
			  vendorWarrantyClaimRepo.deleteById(id);
	        } else {
	            throw new RuntimeException("Warranty Claim not found with ID: " + id);
	        }		
	}

	@Override
	public VendorWarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus) {

	    Optional<VendorWarrantyClaim> existingWarrantyClaim = vendorWarrantyClaimRepo.findById(id);

        if (existingWarrantyClaim.isPresent()) {
        	VendorWarrantyClaim warrantyClaim = existingWarrantyClaim.get();
            warrantyClaim.setStatus(newStatus); // Update the status
            return vendorWarrantyClaimRepo.save(warrantyClaim); // Save the updated entity
        } else {
            throw new RuntimeException("Warranty Claim not found with ID: " + id);
        }
    }

	@Override
	public List<VendorWarrantyClaim> getPendingOrders() {
		// TODO Auto-generated method stub
        return vendorWarrantyClaimRepo.findByStatus(0L); // Fetch orders with status 0
	}

	@Override
	public List<VendorWarrantyClaim> getAcceptedOrders() {
		// TODO Auto-generated method stub
        return vendorWarrantyClaimRepo.findByStatus(1L); // Fetch orders with status 0
	}

	@Override
	public List<VendorWarrantyClaim> getRejectedOrders() {
		// TODO Auto-generated method stub
        return vendorWarrantyClaimRepo.findByStatus(2L); // Fetch orders with status 0
	}

	@Override
	public List<VendorWarrantyClaim> getShipOrders() {
		// TODO Auto-generated method stub
        return vendorWarrantyClaimRepo.findByStatus(3L); // Fetch orders with status 0
	}


  

}
