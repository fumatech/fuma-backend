package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ProductVariations;

@Repository
public interface ProductVariationsRepo extends JpaRepository<ProductVariations, Long> {

	@Query("SELECT pv FROM ProductVariations pv JOIN FETCH pv.product p WHERE p.id = :productId AND pv.id = :productVariationId")
	Optional<ProductVariations> findProductDetailsByIdAndVariation(@Param("productId") Long productId,
			@Param("productVariationId") Long productVariationId);
}
