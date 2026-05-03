package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.WarehouseStock;

@Repository
public interface WarehouseStockRepo extends JpaRepository<WarehouseStock, Long> {

	List<WarehouseStock> findByWarehouseId(Long warehouseId);

	Optional<WarehouseStock> findByWarehouseIdAndProductIdAndProductVariationId(
			Long warehouseId, Long productId, Long productVariationId);
}

