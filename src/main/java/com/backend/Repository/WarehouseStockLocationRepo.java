package com.backend.Repository;

import com.backend.Entity.WarehouseStockLocation;
import com.backend.DTO.WarehouseLocationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseStockLocationRepo extends JpaRepository<WarehouseStockLocation, Long> {
    List<WarehouseStockLocation> findByWarehouseId(Long warehouseId);
    
    Optional<WarehouseStockLocation> findByWarehouseIdAndProductIdAndProductVariationIdAndRackIdAndBinId(
            Long warehouseId, Long productId, Long productVariationId, Long rackId, Long binId);
            
    List<WarehouseStockLocation> findByBinId(Long binId);
    
    List<WarehouseStockLocation> findByWarehouseIdAndProductIdAndProductVariationId(
            Long warehouseId, Long productId, Long productVariationId);

    @Query("SELECT new com.backend.DTO.WarehouseLocationDTO(" +
           "  loc.productId, loc.productVariationId, " +
           "  ws.productName, ws.productVariationName, ws.productSku, " +
           "  loc.rackId, r.rackCode, " +
           "  loc.binId, b.binCode, loc.quantity) " +
           "FROM WarehouseStockLocation loc " +
           "JOIN WarehouseStock ws ON ws.warehouseId = loc.warehouseId " +
           "  AND ws.productId = loc.productId " +
           "  AND (ws.productVariationId = loc.productVariationId OR (ws.productVariationId IS NULL AND loc.productVariationId IS NULL)) " +
           "JOIN WarehouseRack r ON r.id = loc.rackId " +
           "JOIN WarehouseBin b ON b.id = loc.binId " +
           "WHERE loc.warehouseId = :warehouseId")
    List<WarehouseLocationDTO> getInventoryLocations(@Param("warehouseId") Long warehouseId);
}
