package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchaseDIOrder;

@Repository
public interface PurchaseDIOrderRepo extends JpaRepository<PurchaseDIOrder, Long> {

	@Query("SELECT MAX(p.id) FROM PurchaseDIOrder p")
	Long getLastPurchaseDIOrderId();

	@Query("SELECT p FROM PurchaseDIOrder p WHERE p.vendor LIKE CONCAT('%', :vendor, '%')")
	List<PurchaseDIOrder> findByVendorName(String vendor);

	@Query("SELECT COALESCE(SUM(p.netTotalAmount - p.taxAmount), 0) FROM PurchaseDIOrder p")
	BigDecimal totalPurchaseDI();

	@Query("SELECT COALESCE(SUM(p.netTotalAmount),0) FROM PurchaseDIOrder p")
	BigDecimal totalPurchaseDIWithTax();

	@Query("""
			    SELECT d FROM PurchaseDIOrder d
			    WHERE d.purchaseTax IS NOT NULL
			""")
	List<PurchaseDIOrder> findAllWithPurchaseTax();

}
