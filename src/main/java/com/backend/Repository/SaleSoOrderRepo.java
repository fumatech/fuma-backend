package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.SaleSoOrder;
import com.backend.DTO.SaleSoOrderListDTO;

public interface SaleSoOrderRepo extends JpaRepository<SaleSoOrder, Long> {

	@Query("SELECT p FROM SaleSoOrder p WHERE p.franchise LIKE CONCAT('%', :franchise, '%')")

	List<SaleSoOrder> findByFranchise(String franchise);

	@Query("SELECT s FROM SaleSoOrder s WHERE s.referenceNumber LIKE 'FUMASL%' ORDER BY LENGTH(s.referenceNumber) DESC, s.referenceNumber DESC")
	List<SaleSoOrder> findTopOrderByReferenceNumber(Pageable pageable);

	List<SaleSoOrder> findBySaleDateBetween(java.sql.Date start, java.sql.Date end);

	List<SaleSoOrder> findByAddedByAndSaleDateBetween(String addedBy, java.sql.Date start, java.sql.Date end);

	@Query("SELECT COALESCE(SUM(s.netTotalAmount - s.taxAmount), 0) FROM SaleSoOrder s")

	BigDecimal totalSaleSo();

	@Query("SELECT COALESCE(SUM(s.netTotalAmount),0) FROM SaleSoOrder s")
	BigDecimal totalSaleSoWithTax();

	@Query("""
			    SELECT p FROM SaleSoOrder p
			    WHERE p.taxAmount IS NOT NULL
			""")
	List<SaleSoOrder> findAllWithSaleTax();

	@Query("""
			SELECT new com.backend.DTO.SaleSoOrderListDTO(
				s.id,
				s.orderId,
				s.orderRefernceNumber,
				s.franchise,
				s.franchiseId,
				s.customerId,
				s.referenceNumber,
				s.orderedBy,
				s.addedBy,
				s.orderDate,
				s.saleDate,
				s.payTermNumber,
				s.payTermType,
				s.location,
				s.totalItems,
				s.totalSaleItems,
				s.netTotalAmount,
				s.discountType,
				s.discountAmount,
				s.purchaseTax,
				s.taxAmount,
				s.additionalNotes,
				c.franchiseName,
				c.city,
				c.state
				
				
				
			)
			FROM SaleSoOrder s
			LEFT JOIN Customer c ON c.id = s.customerId
			ORDER BY s.id DESC
			""")
	List<SaleSoOrderListDTO> findAllListRows();
	boolean existsByOrderId(String orderId);

}
