package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.FranchisePurchaseOrder;

@Repository
public interface FranchisePurchaseOrderRepo extends JpaRepository<FranchisePurchaseOrder, Long> {

	@Query("SELECT MAX(f.id) FROM FranchisePurchaseOrder f")
	Long getLastFranchisePurchaseOrderId();

	@Query("SELECT f FROM FranchisePurchaseOrder f WHERE f.status = :status")
	List<FranchisePurchaseOrder> findByFranchisePurchaseStatus(@Param("status") Long status);

	@Query("SELECT f.franchisePurchaseOrderId FROM FranchisePurchaseOrder f WHERE f.status = :status")
	List<String> findFranchisePurchaseOrderIdsByStatus(@Param("status") Long status);

	FranchisePurchaseOrder findByFranchisePurchaseOrderId(String franchisePurchaseOrderId);

	@Query("SELECT f.totalShippedItems FROM FranchisePurchaseOrder f WHERE f.franchisePurchaseOrderId = :franchisePurchaseOrderId")
	Long getTotalShippedItems(@Param("franchisePurchaseOrderId") String franchisePurchaseOrderId);
}