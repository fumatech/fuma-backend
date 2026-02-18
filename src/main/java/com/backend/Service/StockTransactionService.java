package com.backend.Service;

import com.backend.Entity.StockTransaction;

import java.util.List;

public interface StockTransactionService {
    // Method to save a new stock transaction
    List<StockTransaction> saveStockTransactions(List<StockTransaction> stockTransactions);

    // Method to retrieve all transactions for a specific product
    List<StockTransaction> getTransactionsByProduct(Long productId);

    // Method to retrieve all transactions for a specific variation
    List<StockTransaction> getTransactionsByVariation(Long variationId);

    // Method to get the history of all transactions (optional)
    List<StockTransaction> getAllTransactions();

    int getCurrentStock(Long productId, Long variationId);

    int getCurrentStockByProduct(Long productId);

    int getCurrentStockByvariation(Long variationId);

    List<StockTransaction> getTransactionsByProductAndVariation(Long productId, Long variationId);

    void validateStockTransactions(List<StockTransaction> stockTransactions);

    java.util.Map<String, Integer> getBulkCurrentStock(List<java.util.Map<String, Long>> requests);

}
