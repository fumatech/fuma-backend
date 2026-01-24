package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.FranchisePurchaseReturn;

@Repository
public interface FranchisePurchaseReturnRepo extends JpaRepository<FranchisePurchaseReturn, Long> {

	@Query("SELECT MAX(f.id) FROM FranchisePurchaseReturn f")
	Long getLastFranchisePurchaseReturnId();

	@Query("SELECT MAX(p.invoiceNumber) FROM FranchisePurchaseReturn p")
	String getLastInvoiceNumber();

	FranchisePurchaseReturn findByFranchisePurchaseReturnId(String id);

	@Query("SELECT f.franchisePurchaseReturnId FROM FranchisePurchaseReturn f WHERE f.status = :status")
	List<String> findReturnIdsByStatus(Long status);

	@Query("SELECT f FROM FranchisePurchaseReturn f WHERE f.status = :status")
	List<FranchisePurchaseReturn> findByReturnStatus(Long status);

	@Query("SELECT f.totalShippedItems FROM FranchisePurchaseReturn f WHERE f.franchisePurchaseReturnId = :id")
	Long getTotalShippedItems(String id);

	@Query("SELECT COALESCE(SUM(pr.netTotalAmount),0) FROM FranchisePurchaseReturn pr")
	BigDecimal totalFranchisePurchaseReturnWithTax();

	@Query("SELECT f FROM FranchisePurchaseReturn f WHERE f.franchiseId = :franchiseId")
	List<FranchisePurchaseReturn> findByFranchiseId(String franchiseId);

	@Query("SELECT f FROM FranchisePurchaseReturn f WHERE f.franchiseId = :franchiseId AND f.status = :status")
	List<FranchisePurchaseReturn> findByFranchiseIdAndStatus(String franchiseId, Long status);

	@Query("SELECT COALESCE(SUM(f.netTotalAmount), 0) FROM FranchisePurchaseReturn f WHERE f.franchiseId = :franchiseId")
	BigDecimal totalReturnAmountByFranchise(String franchiseId);

}
