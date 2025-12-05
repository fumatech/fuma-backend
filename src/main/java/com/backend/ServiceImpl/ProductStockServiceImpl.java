package com.backend.ServiceImpl;

import com.backend.Entity.ProductStock;
import com.backend.Repository.ProductStockRepo;
import com.backend.Service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Autowired
    private ProductStockRepo productStockRepo;

    /**
     * Add multiple ProductStock entries. If a stock with the same productId and variationId
     * already exists, update the totalStock by adding the stockQuantity. Otherwise, create a new entry.
     */
    @Override
    public void addProductStock(List<ProductStock> productStocks) {
        for (ProductStock stock : productStocks) {
            Optional<ProductStock> existingStock = productStockRepo.findByProductIdAndVariationId(
                    stock.getProductId(), stock.getVariationId());
            if (existingStock.isPresent()) {
                ProductStock currentStock = existingStock.get();
                // Update totalStock
                currentStock.setStockQuantity(stock.getStockQuantity());
                currentStock.setTotalStock(currentStock.getTotalStock() + stock.getStockQuantity());
                productStockRepo.save(currentStock);
            } else {
                // Set the totalStock for new entries
                stock.setTotalStock(stock.getStockQuantity());
                productStockRepo.save(stock);
            }
        }
    }

    /**
     * Update a specific ProductStock by productId and variationId. Add the new stockQuantity to the existing totalStock
     * and update other fields as necessary.
     */
    @Override
    public ProductStock updateProductStock(Long productId, Long variationId, ProductStock productStock) {
        Optional<ProductStock> existingStock = productStockRepo.findByProductIdAndVariationId(productId, variationId);
        if (existingStock.isPresent()) {
            ProductStock currentStock = existingStock.get();
            // Update totalStock and other fields
            currentStock.setStockQuantity(productStock.getStockQuantity());
            currentStock.setTotalStock(currentStock.getTotalStock() + productStock.getStockQuantity());
            currentStock.setUnitCostBeforeTax(productStock.getUnitCostBeforeTax());
            currentStock.setSubTotalBeforeTax(productStock.getSubTotalBeforeTax());
            currentStock.setDate(productStock.getDate());
            currentStock.setNote(productStock.getNote());
            return productStockRepo.save(currentStock);
        }
        throw new RuntimeException("Product stock not found with productId: " + productId + " and variationId: " + variationId);
    }

    /**
     * Delete a ProductStock entry by productId and variationId.
     */
    @Override
    public void deleteProductStock(Long productId, Long variationId) {
        Optional<ProductStock> existingStock = productStockRepo.findByProductIdAndVariationId(productId, variationId);
        if (existingStock.isPresent()) {
            productStockRepo.delete(existingStock.get());
        } else {
            throw new RuntimeException("Product stock not found with productId: " + productId + " and variationId: " + variationId);
        }
    }

    /**
     * Get a ProductStock entry by productId and variationId.
     */
    @Override
    public ProductStock getProductStockByProductIdAndVariationId(Long productId, Long variationId) {
        return productStockRepo.findByProductIdAndVariationId(productId, variationId)
                .orElseThrow(() -> new RuntimeException("Product stock not found with productId: " + productId + " and variationId: " + variationId));
    }

    /**
     * Get all ProductStock entries.
     */
    @Override
    public List<ProductStock> getAllProductStock() {
        return productStockRepo.findAll();
    }

    /**
     * Get all ProductStock entries for a given productId.
     */
    @Override
    public List<ProductStock> getProductStockByProductId(Long productId) {
        return productStockRepo.findByProductId(productId);
    }

    /**
     * Calculate the total stock for a given variationId by summing the totalStock of all entries with the same variationId.
     */
    @Override
    public Long getTotalStockByVariationId(Long variationId) {
        List<ProductStock> stocks = productStockRepo.findByVariationId(variationId);
        return stocks.stream()
                .mapToLong(ProductStock::getTotalStock)
                .sum();
    }
}
