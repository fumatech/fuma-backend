package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.StockAdjustment;
import com.backend.Repository.StockAdjustmentRepo;
import com.backend.Service.StockAdjustmentService;

@Service
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

    @Autowired
    private StockAdjustmentRepo stockAdjustmentRepo;

    @Override
    public StockAdjustment saveStockAdjustment(StockAdjustment stockAdjustment) {
        if (stockAdjustment.getStockAdjustmentItems() != null) {
            stockAdjustment.getStockAdjustmentItems().forEach(item -> item.setStockAdjustment(stockAdjustment));
        }
        if (stockAdjustment.getStockTransaction() != null) {
            stockAdjustment.getStockTransaction().forEach(item -> item.setStockAdjustment(stockAdjustment));
        }
        return stockAdjustmentRepo.save(stockAdjustment);
    }

    // Retrieve all Stock Adjustments
    @Override
    public List<StockAdjustment> getAllStockAdjustments() {
        return stockAdjustmentRepo.findAll();
    }

    // Retrieve a Stock Adjustment by ID
    @Override
    public Optional<StockAdjustment> getStockAdjustmentById(Long id) {
        return stockAdjustmentRepo.findById(id);
    }

    // Update an existing Stock Adjustment
    @Override
    public StockAdjustment updateStockAdjustment(Long id, StockAdjustment stockAdjustment) {
        Optional<StockAdjustment> existingStockAdjustment = stockAdjustmentRepo.findById(id);

        if (existingStockAdjustment.isPresent()) {
            StockAdjustment updatedStockAdjustment = existingStockAdjustment.get();

            // Update fields
            updatedStockAdjustment.setBusinessLocation(stockAdjustment.getBusinessLocation());
            updatedStockAdjustment.setReferenceNumber(stockAdjustment.getReferenceNumber());
            updatedStockAdjustment.setDate(stockAdjustment.getDate());
            updatedStockAdjustment.setAdjustmentType(stockAdjustment.getAdjustmentType());
            updatedStockAdjustment.setTotalAmount(stockAdjustment.getTotalAmount());
            updatedStockAdjustment.setTotalUnits(stockAdjustment.getTotalUnits());
            updatedStockAdjustment.setAmountRecovered(stockAdjustment.getAmountRecovered());
            updatedStockAdjustment.setReason(stockAdjustment.getReason());

            // Update Stock Adjustment Items
            if (stockAdjustment.getStockAdjustmentItems() != null) {
                stockAdjustment.getStockAdjustmentItems().forEach(item -> item.setStockAdjustment(updatedStockAdjustment));
                updatedStockAdjustment.setStockAdjustmentItems(stockAdjustment.getStockAdjustmentItems());
            }

            return stockAdjustmentRepo.save(updatedStockAdjustment);
        } else {
            throw new RuntimeException("Stock Adjustment not found with ID: " + id);
        }
    }

    // Delete a Stock Adjustment by ID
    @Override
    public void deleteStockAdjustment(Long id) {
        if (stockAdjustmentRepo.existsById(id)) {
            stockAdjustmentRepo.deleteById(id);
        } else {
            throw new RuntimeException("Stock Adjustment not found with ID: " + id);
        }
    }
}
