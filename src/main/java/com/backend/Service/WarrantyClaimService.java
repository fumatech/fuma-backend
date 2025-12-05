package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.WarrantyClaim;

public interface WarrantyClaimService {
    
    WarrantyClaim saveWarrantyClaim(WarrantyClaim warrantyClaim);
    
    List<WarrantyClaim> getAllWarrantyClaims();
    
    Optional<WarrantyClaim> getWarrantyClaimById(Long id);

    WarrantyClaim updateWarrantyClaim(Long id, WarrantyClaim warrantyClaim); 
    
    void deleteWarrantyClaim(Long id); 
    
     WarrantyClaim updateWarrantyClaimStatus(Long id, Long newStatus) ;

}
