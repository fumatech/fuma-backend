package com.backend.ServiceImpl;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.*;
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
                if (isOutTransaction(transaction.getTransactionType())) {
                    Long productId = transaction.getProductId();
                    Long variationId = transaction.getVariationId();
                    int currentStock = (variationId != null && variationId > 0)
                            ? getCurrentStock(productId, variationId)
                            : getCurrentStockByProduct(productId);

                    if (currentStock < transaction.getQuantity()) {
                        throw new RuntimeException("Insufficient Stock for Product ID: " + transaction.getProductId()
                                + " | Available: " + currentStock);
                    }
                }
            }
        }
    }

    private boolean isOutTransaction(String type) {
        if (type == null) return false;
        String t = type.toLowerCase();
        return t.equals("di_sale") || t.equals("so_sale") || t.equals("transfer_out")
                || t.equals("purchase_return") || t.equals("product_replaced") || t.equals("adjustment")
                || t.equals("dispatch") || t.equals("transfer_out");
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
    public Map<String, Integer> getBulkCurrentStock(List<Map<String, Long>> requests) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map<String, Long> req : requests) {
            Long productId = req.get("productId");
            Long variationId = req.get("variationId");
            String key = productId + "_" + (variationId != null ? variationId : "null");
            result.put(key, getCurrentStock(productId, variationId));
        }
        return result;
    }

    @Override
    public void log(Long warehouseId, Long productId, Long variationId, int quantity, String type, String note, String referenceId, Object entity) {
        StockTransaction transaction = new StockTransaction();
        transaction.setWarehouseId(warehouseId);
        transaction.setProductId(productId);
        transaction.setVariationId(variationId);
        transaction.setQuantity(quantity);
        transaction.setTransactionType(type);
        transaction.setNote(note);
        transaction.setReferenceId(referenceId);
        transaction.setDate(new Date(System.currentTimeMillis()));

        if (entity instanceof PurchasePoOrder) {
            transaction.setPurchasePoOrder((PurchasePoOrder) entity);
        } else if (entity instanceof SaleSoOrder) {
            transaction.setSaleSoOrder((SaleSoOrder) entity);
        } else if (entity instanceof StockTransfer) {
            transaction.setStockTransfer((StockTransfer) entity);
        }

        stockTransactionRepo.save(transaction);
    }
}
