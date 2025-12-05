package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.WarrantyClaim;
import com.backend.Repository.WarrantyClaimRepo;
import com.backend.Service.WarrantyClaimService;

@Service
public class WarrantyClaimServiceImpl implements WarrantyClaimService {

    @Autowired
    private WarrantyClaimRepo warrantyClaimRepo;

    @Override
    public WarrantyClaim saveWarrantyClaim(WarrantyClaim warrantyClaim) {
        // Setting the parent entity for the related child entities (if any)
        if (warrantyClaim.getWarrantyClaimItems() != null) {
            warrantyClaim.getWarrantyClaimItems().forEach(item -> item.setWarrantyClaim(warrantyClaim));
        }
        if (warrantyClaim.getStockTransaction() != null) {
            warrantyClaim.getStockTransaction().forEach(item -> item.setWarrantyClaim(warrantyClaim));
        }
        return warrantyClaimRepo.save(warrantyClaim);
    }

    // Retrieve all Warranty Claims
    @Override
    public List<WarrantyClaim> getAllWarrantyClaims() {
        return warrantyClaimRepo.findAll();
    }

    // Retrieve a Warranty Claim by ID
    @Override
    public Optional<WarrantyClaim> getWarrantyClaimById(Long id) {
        return warrantyClaimRepo.findById(id);
    }

    // Update an existing Warranty Claim
    @Override
    public WarrantyClaim updateWarrantyClaim(Long id, WarrantyClaim warrantyClaim) {
        Optional<WarrantyClaim> existingWarrantyClaim = warrantyClaimRepo.findById(id);

        if (existingWarrantyClaim.isPresent()) {
            WarrantyClaim updatedWarrantyClaim = existingWarrantyClaim.get();

            updatedWarrantyClaim.setBusinessLocation(warrantyClaim.getBusinessLocation());
            updatedWarrantyClaim.setReferenceNumber(warrantyClaim.getReferenceNumber());
            updatedWarrantyClaim.setDate(warrantyClaim.getDate());
            updatedWarrantyClaim.setStatus(warrantyClaim.getStatus());   
            updatedWarrantyClaim.setTotalAmount(warrantyClaim.getTotalAmount());
            updatedWarrantyClaim.setUpdatedTotalAmount(warrantyClaim.getUpdatedTotalAmount());
            updatedWarrantyClaim.setUpdatedTotalUnits(warrantyClaim.getUpdatedTotalUnits());
            updatedWarrantyClaim.setTotalUnits(warrantyClaim.getTotalUnits());
            updatedWarrantyClaim.setReason(warrantyClaim.getReason());

             if (warrantyClaim.getWarrantyClaimItems() != null) {
                warrantyClaim.getWarrantyClaimItems().forEach(item -> item.setWarrantyClaim(updatedWarrantyClaim));
                updatedWarrantyClaim.setWarrantyClaimItems(warrantyClaim.getWarrantyClaimItems());
            }

            // Update Warranty Transactions (matching entity)
            if (warrantyClaim.getStockTransaction() != null) {
                warrantyClaim.getStockTransaction().forEach(item -> item.setWarrantyClaim(updatedWarrantyClaim));
                updatedWarrantyClaim.setStockTransaction(warrantyClaim.getStockTransaction());
            }

            return warrantyClaimRepo.save(updatedWarrantyClaim);
        } else {
            throw new RuntimeException("Warranty Claim not found with ID: " + id);
        }
    }

    // Delete a Warranty Claim by ID
    @Override
    public void deleteWarrantyClaim(Long id) {
        if (warrantyClaimRepo.existsById(id)) {
            warrantyClaimRepo.deleteById(id);
        } else {
            throw new RuntimeException("Warranty Claim not found with ID: " + id);
        }
    }
    
    @Override
    public WarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus) {
        Optional<WarrantyClaim> existingWarrantyClaim = warrantyClaimRepo.findById(id);

        if (existingWarrantyClaim.isPresent()) {
            WarrantyClaim warrantyClaim = existingWarrantyClaim.get();
            warrantyClaim.setStatus(newStatus); // Update the status
            return warrantyClaimRepo.save(warrantyClaim); // Save the updated entity
        } else {
            throw new RuntimeException("Warranty Claim not found with ID: " + id);
        }
    }

}
