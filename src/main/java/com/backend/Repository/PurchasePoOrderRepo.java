package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchasePoOrder;

@Repository
public interface PurchasePoOrderRepo extends JpaRepository<PurchasePoOrder, Long> {

	@Query("SELECT p FROM PurchasePoOrder p WHERE p.vendor LIKE CONCAT('%', :vendor, '%')")
	List<PurchasePoOrder> findByVendorName(String vendor);

	@Query("SELECT COALESCE(SUM(p.netTotalAmount - p.taxAmount), 0) FROM PurchasePoOrder p")
	BigDecimal totalPurchasePo();

	@Query("SELECT COALESCE(SUM(p.netTotalAmount),0) FROM PurchasePoOrder p")
	BigDecimal totalPurchasePoWithTax();

	@Query("""
			    SELECT p FROM PurchasePoOrder p
			    WHERE p.taxAmount IS NOT NULL
			""")
	List<PurchasePoOrder> findAllWithPurchaseTax();

}
