package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchaseReturn;

@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturn, Long> {

	// Corrected method name to match the field name in PurchaseOrder entity
	PurchaseReturn findByPurchaseReturnId(String purchaseReturnId);

	// Query to get the last numeric part of the purchase order ID
	@Query("SELECT MAX(p.id) FROM PurchaseReturn p")
	Long getLastPurchaseReturnId();

	@Query("SELECT MAX(p.invoiceNumber) FROM PurchaseReturn p")
	String getLastInvoiceNumber();

	@Query("SELECT p.purchaseReturnId FROM PurchaseReturn p WHERE p.status = :status")
	List<String> findPurchaseReturnIdsByStatus(Long status);

	// Get a list of PurchaseOrders by status
	@Query("SELECT p FROM PurchaseReturn p WHERE p.status = :status")
	List<PurchaseReturn> findByPurchaseReturnStatus(@Param("status") Long status);

	// Query to fetch the totalShippedItems field for a given purchaseOrderId
	@Query("SELECT p.totalShippedItems FROM PurchaseReturn p WHERE p.purchaseReturnId = :purchaseReturnId")
	Long getTotalShippedItems(@Param("purchaseReturnId") String purchaseReturnId);

	@Query("SELECT COALESCE(SUM(pr.totalAmount),0) FROM PurchaseReturn pr")
	BigDecimal totalPurchaseReturnWithTax();

}
