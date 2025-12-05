package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.VendorWarrantyClaim;

@Repository
public interface VendorWarrantyClaimRepo extends JpaRepository<VendorWarrantyClaim, Long> {

	
    // Get a list of PurchaseOrders by status
    @Query("SELECT p FROM VendorWarrantyClaim p WHERE p.status = :status")
    List<VendorWarrantyClaim> findByStatus(@Param("status") Long status);
}
