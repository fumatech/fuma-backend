package com.backend.Service;

import com.backend.Entity.WarehouseRack;
import com.backend.Entity.WarehouseBin;
import com.backend.DTO.WarehouseLocationDTO;
import java.util.List;
import java.util.Map;

public interface WarehousePutAwayService {
    List<WarehouseRack> getRacksByWarehouse(Long warehouseId);
    WarehouseRack createRack(WarehouseRack rack);
    List<WarehouseBin> getBinsByRack(Long rackId);
    WarehouseBin createBin(WarehouseBin bin);
    List<Map<String, Object>> getUnallocatedStock(Long warehouseId);
    void performPutAway(Long warehouseId, Long productId, Long productVariationId, Long rackId, Long binId, Long quantity, Long userId);
    List<WarehouseLocationDTO> getInventoryLocations(Long warehouseId);
}
