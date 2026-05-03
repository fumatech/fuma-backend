package com.backend.Service;

import java.util.List;
import java.util.Map;

import com.backend.Entity.StockTransferItems;
import com.backend.Entity.WarehouseStock;

public interface WarehouseStockService {

	List<WarehouseStock> getByWarehouseId(Long warehouseId);

	void allocateStock(Long warehouseId, List<StockTransferItems> items);

	Map<String, Integer> getBulkCurrentStock(Long warehouseId, List<Map<String, Long>> requests);
	
	void updateStock(Long warehouseId, Long productId, Long variationId, Long quantity, String transactionType, String referenceId, Object referenceEntity);
}
