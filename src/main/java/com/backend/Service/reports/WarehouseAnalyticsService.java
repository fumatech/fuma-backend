package com.backend.Service.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.Repository.reports.StockMovementLogRepository;
import com.backend.Repository.reports.InventoryAgingRepository;
import com.backend.Repository.reports.WarehousePerformanceMetricsRepository;
import com.backend.Entity.reports.StockMovementLog;
import com.backend.Entity.reports.InventoryAging;
import com.backend.Entity.reports.WarehousePerformanceMetrics;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseAnalyticsService {

    @Autowired
    private StockMovementLogRepository stockMovementLogRepository;
    @Autowired
    private InventoryAgingRepository inventoryAgingRepository;
    @Autowired
    private WarehousePerformanceMetricsRepository performanceMetricsRepository;

    // Fetch stock report for a warehouse
    public List<StockMovementLog> getStockReport(Long warehouseId, String category, String date, String status) {
        // For demo: return all stock movements for warehouse
        return stockMovementLogRepository.findByWarehouseId(warehouseId);
    }

    // Fetch movement logs
    public List<StockMovementLog> getMovementLogs(Long warehouseId) {
        return stockMovementLogRepository.findByWarehouseId(warehouseId);
    }

    // Fetch inventory aging report
    public List<InventoryAging> getAgingReport(Long warehouseId) {
        return inventoryAgingRepository.findByWarehouseId(warehouseId);
    }

    // Fetch warehouse performance metrics
    public List<WarehousePerformanceMetrics> getPerformanceMetrics(Long warehouseId) {
        return performanceMetricsRepository.findByWarehouseId(warehouseId);
    }

    // Fetch turnover analysis (stub)
    public List<StockMovementLog> getTurnoverAnalysis(Long warehouseId) {
        // For demo: return all stock movements for warehouse
        return stockMovementLogRepository.findByWarehouseId(warehouseId);
    }
}
