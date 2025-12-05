package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ProductStock;

@Repository
public interface ProductStockRepo extends JpaRepository<ProductStock, Long> {

    // Find all product stocks by productId
    @Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId")
    List<ProductStock> findByProductId(@Param("productId") Long productId);

    // Find all product stocks by variationId
    List<ProductStock> findByVariationId(Long variationId);

    // Find a specific product stock by productId and variationId
    @Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId AND ps.variationId = :variationId")
    Optional<ProductStock> findByProductIdAndVariationId(@Param("productId") Long productId, @Param("variationId") Long variationId);
}
