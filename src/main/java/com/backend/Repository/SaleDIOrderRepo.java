package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.SaleDIOrder;

public interface SaleDIOrderRepo extends JpaRepository<SaleDIOrder, Long> {

	@Query("SELECT MAX(p.id) FROM SaleDIOrder p")
	Long getLastSaleDIOrderId();

	@Query("SELECT p FROM SaleDIOrder p WHERE p.franchise LIKE CONCAT('%', :franchise, '%')")
	List<SaleDIOrder> findByFranchise(String franchise);

	@Query("SELECT s FROM SaleDIOrder s WHERE s.referenceNumber LIKE 'FUMADIS%' ORDER BY LENGTH(s.referenceNumber) DESC, s.referenceNumber DESC")
	List<SaleDIOrder> findTopOrderByReferenceNumber(Pageable pageable);

	@Query("SELECT COALESCE(SUM(s.netTotalAmount - s.taxAmount), 0) FROM SaleDIOrder s")
	BigDecimal totalSaleDI();

	@Query("SELECT COALESCE(SUM(s.netTotalAmount),0) FROM SaleDIOrder s")
	BigDecimal totalSaleDIWithTax();

	@Query("""
			    SELECT d FROM SaleDIOrder d
			    WHERE d.purchaseTax IS NOT NULL
			""")
	List<SaleDIOrder> findAllWithSaleTax();

}
