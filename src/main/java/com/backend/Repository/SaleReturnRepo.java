package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.SaleReturn;
@Repository
public interface SaleReturnRepo extends JpaRepository<SaleReturn, Long> {

    // Corrected method name to match the field name in SaleReturn entity
    SaleReturn findByPurchaseReturnId(String purchaseReturnId);

    // Other methods
    @Query("SELECT MAX(p.id) FROM SaleReturn p")
    Long getLastSaleReturnId();
    
    @Query("SELECT p.purchaseReturnId FROM SaleReturn p WHERE p.status = :status")
    List<String> findSaleReturnIdsByStatus(Long status);

    @Query("SELECT p FROM SaleReturn p WHERE p.status = :status")
    List<SaleReturn> findBySaleReturnStatus(@Param("status") Long status);

    @Query("SELECT p.totalShippedItems FROM SaleReturn p WHERE p.purchaseReturnId = :purchaseReturnId")
    Long getTotalShippedItems(@Param("purchaseReturnId") String purchaseReturnId);
}
