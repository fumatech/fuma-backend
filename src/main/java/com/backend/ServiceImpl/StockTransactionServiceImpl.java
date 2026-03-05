package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.StockTransaction;
import com.backend.Repository.StockTransactionRepo;
import com.backend.Service.StockTransactionService;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

    @Autowired
    private StockTransactionRepo stockTransactionRepo;

    @Override
    @Transactional
    public List<StockTransaction> saveStockTransactions(List<StockTransaction> stockTransactions) {
        validateStockTransactions(stockTransactions);
        return stockTransactionRepo.saveAll(stockTransactions);
    }

    @Override
    public void validateStockTransactions(List<StockTransaction> stockTransactions) {
        if (stockTransactions != null) {
            for (StockTransaction transaction : stockTransactions) {
                // Only validate for OUT transactions
                if (isOutTransaction(transaction.getTransactionType())) {
                    Long productId = transaction.getProductId();
                    Long variationId = transaction.getVariationId();
                    int currentStock = (variationId != null && variationId > 0) ? getCurrentStock(productId, variationId) : getCurrentStockByProduct(productId);

                    if (currentStock < transaction.getQuantity()) {
                        throw new RuntimeException("Insufficient Stock for Product ID: " + transaction.getProductId() + " | Available: " + currentStock);
                    }
                }
            }
        }
    }

    private boolean isOutTransaction(String type) {

        if (type == null) {
            return false;
        }
        return type.equals("di_sale") || type.equals("so_sale") || type.equals("transfer_out") || type.equals("purchase_return") || type.equals("product_replaced") || type.equals("adjustment");
    }

    @Override
    public List<StockTransaction> getTransactionsByProduct(Long productId) {

        return stockTransactionRepo.findByProductId(productId);

    }

    @Override
    public List<StockTransaction> getTransactionsByVariation(Long variationId) {

        return stockTransactionRepo.findByVariationId(variationId);

    }

    @Override
    public List<StockTransaction> getAllTransactions() {

        return stockTransactionRepo.findAll();

    }

    @Override
    public List<StockTransaction> getTransactionsByProductAndVariation(Long productId, Long variationId) {

        return stockTransactionRepo.findByProductIdAndVariationId(productId, variationId);

    }

    @Override
    public int getCurrentStock(Long productId, Long variationId) {

        if (variationId == null || variationId <= 0) {
            return stockTransactionRepo.calculateCurrentStockByProduct(productId);

        }

        return stockTransactionRepo.calculateCurrentStock(productId, variationId);

    }

    @Override
    public int getCurrentStockByProduct(Long productId) {

        return stockTransactionRepo.calculateCurrentStockByProduct(productId);

    }

    @Override
    public int getCurrentStockByvariation(Long variationId) {

        return stockTransactionRepo.calculateCurrentStockByVariation(variationId);

    }

    @Override

    public java.util.Map<String, Integer> getBulkCurrentStock(
            java.util.List<java.util.Map<String, Long>> requests) {

        // LinkedHashMap is used to maintain insertion order
        java.util.Map<String, Integer> result = new java.util.LinkedHashMap<>();

        // Loop through each request item in the list
        for (java.util.Map<String, Long> req : requests) {

            // Extract productId from request map
            Long productId = req.get("productId");

            // Extract variationId from request map (can be null)
            Long variationId = req.get("variationId");

            // Create unique key using productId and variationId
            // Format: productId_variationId (example: 101_5 or 101_null)
            String key = productId + "_"
                    + (variationId != null ? variationId : "null");

            // If variationId is present and greater than 0
            // then calculate stock based on product + variation
            if (variationId != null && variationId > 0) {
                result.put(
                        key,
                        stockTransactionRepo.calculateCurrentStock(productId, variationId)
                );

            } else {
                // If variationId is null or 0
                // calculate stock only based on productId
                result.put(
                        key,
                        stockTransactionRepo.calculateCurrentStockByProduct(productId)
                );
            }
        }

        // Return map containing stock values for all requested items
        return result;
    }
}
