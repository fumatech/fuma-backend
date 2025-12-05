package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.backend.Entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {
    
    // Corrected method name to match the field name in PurchaseOrder entity
    PurchaseOrder findByPurchaseOrderId(String purchaseOrderId);
    // Query to get the last numeric part of the purchase order ID
    @Query("SELECT MAX(p.id) FROM PurchaseOrder p")
    Long getLastPurchaseOrderId();
    
    
    @Query("SELECT p.purchaseOrderId FROM PurchaseOrder p WHERE p.status = :status")
    List<String> findPurchaseOrderIdsByStatus(Long status);
    

    
    // Get a list of PurchaseOrders by status
    @Query("SELECT p FROM PurchaseOrder p WHERE p.status = :status")
    List<PurchaseOrder> findByPurchaseStatus(@Param("status") Long status);
    
    
    
 // Query to fetch the totalShippedItems field for a given purchaseOrderId
    @Query("SELECT p.totalShippedItems FROM PurchaseOrder p WHERE p.purchaseOrderId = :purchaseOrderId")
    Long getTotalShippedItems(@Param("purchaseOrderId") String purchaseOrderId);
    

    

}
