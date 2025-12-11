package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.Entity.StockTransaction;

public interface StockTransactionRepo extends JpaRepository<StockTransaction, Long> {

	// Custom query to find stock transactions by productId
	List<StockTransaction> findByProductId(Long productId);

	// Custom query to find stock transactions by variationId
	List<StockTransaction> findByVariationId(Long variationId);

	// Custom query to find stock transactions by productId and variationId
	List<StockTransaction> findByProductIdAndVariationId(Long productId, Long variationId);

	@Query("SELECT st FROM StockTransaction st WHERE st.productId = :productId AND st.variationId = :variationId")
	List<StockTransaction> getTransactions(@Param("productId") Long productId, @Param("variationId") Long variationId);

}
