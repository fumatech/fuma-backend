package com.backend.Repository;

import com.backend.Entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepo extends JpaRepository<StockTransaction, Long> {

    // Custom query to find stock transactions by productId
    List<StockTransaction> findByProductId(Long productId);

    // Custom query to find stock transactions by variationId
    List<StockTransaction> findByVariationId(Long variationId);

    // Custom query to find stock transactions by productId and variationId
    List<StockTransaction> findByProductIdAndVariationId(Long productId, Long variationId);
}
