package com.backend.ServiceImpl;

import com.backend.Entity.WarehouseStock;
import com.backend.Entity.StockTransferItems;
import com.backend.Repository.ProductRepo;
import com.backend.Repository.WarehouseStockRepo;
import com.backend.Service.StockTransactionService;
import com.backend.Service.WarehouseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class WarehouseStockServiceImpl implements WarehouseStockService {

    @Autowired
    private WarehouseStockRepo warehouseStockRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private StockTransactionService stockTransactionService;

    @Override
    public List<WarehouseStock> getByWarehouseId(Long warehouseId) {
        return warehouseStockRepo.findByWarehouseId(warehouseId);
    }

    @Override
    public void allocateStock(Long warehouseId, List<StockTransferItems> items) {
        if (warehouseId == null || items == null || items.isEmpty()) {
            return;
        }

        for (StockTransferItems item : items) {
            Long variationId = item.getProductVariationId();
            Optional<WarehouseStock> existingOpt = warehouseStockRepo
                    .findByWarehouseIdAndProductIdAndProductVariationId(
                            warehouseId, item.getProductId(), variationId);

            WarehouseStock stock = existingOpt.orElseGet(WarehouseStock::new);
            stock.setWarehouseId(warehouseId);
            stock.setProductId(item.getProductId());
            stock.setProductVariationId(variationId);
            stock.setProductName(item.getProductName());
            stock.setProductSku(item.getProductSku());
            stock.setProductVariationName(item.getProductVariationName());
            Long currentQty = stock.getQuantity() == null ? 0L : stock.getQuantity();
            Long addQty = item.getQuantity() == null ? 0L : item.getQuantity();
            stock.setQuantity(currentQty + addQty);

            warehouseStockRepo.save(stock);
            
            // Audit Log
            stockTransactionService.log(warehouseId, item.getProductId(), variationId, addQty.intValue(), "TRANSFER_IN", "Allocated from Transfer", null, null);
        }
    }

    @Override
    public void updateStock(Long warehouseId, Long productId, Long variationId, Long quantity, String transactionType, String referenceId, Object referenceEntity) {
        if (warehouseId == null || productId == null || quantity == null) {
            return;
        }

        Optional<WarehouseStock> stockOpt = warehouseStockRepo.findByWarehouseIdAndProductIdAndProductVariationId(warehouseId, productId, variationId);
        WarehouseStock stock;
        if (stockOpt.isPresent()) {
            stock = stockOpt.get();
        } else {
            stock = new WarehouseStock();
            stock.setWarehouseId(warehouseId);
            stock.setProductId(productId);
            stock.setProductVariationId(variationId);
            productRepo.findById(productId).ifPresent(p -> {
                stock.setProductName(p.getProductName());
                stock.setProductSku(p.getSku());
            });
        }

        Long currentQty = stock.getQuantity() == null ? 0L : stock.getQuantity();
        boolean isPlus = isIncrementType(transactionType);

        // PRODUCTION VALIDATION: Prevent negative stock
        if (!isPlus && (currentQty - quantity < 0)) {
            throw new RuntimeException("Insufficient stock in warehouse for Product ID: " + productId + ". Available: " + currentQty + ", Requested: " + quantity);
        }

        stock.setQuantity(isPlus ? currentQty + quantity : currentQty - quantity);
        warehouseStockRepo.save(stock);

        // Audit Log
        stockTransactionService.log(warehouseId, productId, variationId, quantity.intValue(), transactionType, "Operation: " + transactionType, referenceId, referenceEntity);
    }

    @Override
    public Map<String, Integer> getBulkCurrentStock(Long warehouseId, List<Map<String, Long>> requests) {
        Map<String, Integer> result = new LinkedHashMap<>();
        if (warehouseId == null || requests == null) {
            return result;
        }

        List<WarehouseStock> stocks = warehouseStockRepo.findByWarehouseId(warehouseId);
        Map<String, Integer> stockMap = new LinkedHashMap<>();
        for (WarehouseStock stock : stocks) {
            String key = stock.getProductId() + "_" + (stock.getProductVariationId() != null ? stock.getProductVariationId() : "null");
            int qty = stock.getQuantity() == null ? 0 : stock.getQuantity().intValue();
            stockMap.put(key, qty);
        }

        for (Map<String, Long> req : requests) {
            Long productId = req.get("productId");
            Long variationId = req.get("variationId");
            String key = productId + "_" + (variationId != null ? variationId : "null");
            result.put(key, stockMap.getOrDefault(key, 0));
        }

        return result;
    }

    private boolean isIncrementType(String type) {
        String t = type.toLowerCase();
        return List.of("inward", "purchase", "transfer_in", "return_in", "opening_stock", "po_purchase", "di_purchase", "open_stock").contains(t);
    }
}
