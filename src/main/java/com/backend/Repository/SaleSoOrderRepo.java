package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.SaleSoOrder;

public interface SaleSoOrderRepo extends JpaRepository<SaleSoOrder, Long> {

	@Query("SELECT p FROM SaleSoOrder p WHERE p.franchise LIKE CONCAT('%', :franchise, '%')")
	List<SaleSoOrder> findByFranchise(String franchise);

	@Query("SELECT s FROM SaleSoOrder s WHERE s.referenceNumber LIKE 'FUMASL%' ORDER BY LENGTH(s.referenceNumber) DESC, s.referenceNumber DESC")
	List<SaleSoOrder> findTopOrderByReferenceNumber(Pageable pageable);

	@Query("SELECT COALESCE(SUM(s.netTotalAmount - s.taxAmount), 0) FROM SaleSoOrder s")
	BigDecimal totalSaleSo();

	@Query("SELECT COALESCE(SUM(s.netTotalAmount),0) FROM SaleSoOrder s")
	BigDecimal totalSaleSoWithTax();

}
