package com.backend.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	@Query("SELECT p FROM Transaction p WHERE p.vendor LIKE CONCAT('%', :vendor, '%')")
	List<Transaction> findByVendorName(String vendor);

	@Query("SELECT p FROM Transaction p WHERE p.franchiseName LIKE CONCAT('%', :franchiseName, '%')")
	List<Transaction> findByfranchiseName(String franchiseName);

	@Query("""
			SELECT COALESCE(SUM(t.amount), 0)
			FROM Transaction t
			WHERE LOWER(t.transactionType) IN ('purchase')
			""")
	BigDecimal totalPurchasePaid();

	@Query("""
			SELECT COALESCE(SUM(t.amount), 0)
			FROM Transaction t
			WHERE LOWER(t.transactionType) IN ('sale')
			""")
	BigDecimal totalSaleReceived();

	@Query("SELECT t FROM Transaction t WHERE LOWER(t.transactionType) = LOWER(:type)")
	List<Transaction> findByType(String type);

	List<Transaction> findByPayrollEmployee_Id(Long payrollEmployeeId);

}
