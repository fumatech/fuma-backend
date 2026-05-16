package com.backend.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.StockTransfer;
import com.backend.Entity.WarehouseStock;
import com.backend.Repository.StockTransferRepo;
import com.backend.Repository.WarehouseStockRepo;

import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.Comparator;

@RestController
@RequestMapping("/warehouse-dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class WarehouseDashboardController {

    @Autowired
    private WarehouseStockRepo warehouseStockRepo;

    @Autowired
    private StockTransferRepo stockTransferRepo;

    @GetMapping("/summary/{warehouseId}")
    public ResponseEntity<Map<String, Object>> getDashboardSummary(@PathVariable Long warehouseId) {
        Map<String, Object> summary = new HashMap<>();

        // 1. Fetch all stock items for this warehouse
        List<WarehouseStock> stocks = warehouseStockRepo.findByWarehouseId(warehouseId);

        // 2. Calculate Stats
        long totalProducts = stocks.size();
        long totalStock = stocks.stream().mapToLong(s -> s.getQuantity() != null ? s.getQuantity() : 0L).sum();

        int lowStockThreshold = 10;
        List<WarehouseStock> lowStockItems = stocks.stream()
                .filter(s -> (s.getQuantity() != null ? s.getQuantity() : 0L) < lowStockThreshold)
                .collect(Collectors.toList());

        long lowStockCount = lowStockItems.size();

        summary.put("totalProducts", totalProducts);
        summary.put("totalStock", totalStock);
        summary.put("lowStockCount", lowStockCount);
        summary.put("lowStockItems", lowStockItems.stream().limit(10).collect(Collectors.toList())); // Limit to 10 for dashboard preview

        // 3. Fetch Recent Activity (Stock Transfers to this warehouse)
        List<StockTransfer> recentTransfers = stockTransferRepo.findByTargetWarehouseIdOrderByDateDesc(warehouseId);

        List<Map<String, Object>> activityFeed = new ArrayList<>();
        int limit = Math.min(recentTransfers.size(), 10);
        for (int i = 0; i < limit; i++) {
            StockTransfer transfer = recentTransfers.get(i);
            Map<String, Object> activity = new HashMap<>();
            activity.put("id", transfer.getId());
            activity.put("date", transfer.getDate());
            activity.put("referenceNumber", transfer.getReferenceNumber());
            activity.put("status", transfer.getStatus());
            activity.put("type", transfer.getTransferType());
            activity.put("itemCount", transfer.getStockTransferItems() != null ? transfer.getStockTransferItems().size() : 0);
            activityFeed.add(activity);
        }

        summary.put("recentActivity", activityFeed);

        return ResponseEntity.ok(summary);
    }

    /**
     * Dedicated endpoint for low stock items (for detailed view)
     */
    @GetMapping("/low-stock/{warehouseId}")
    public ResponseEntity<List<WarehouseStock>> getLowStockItems(@PathVariable Long warehouseId,
            @RequestParam(defaultValue = "10") int threshold) {
        List<WarehouseStock> stocks = warehouseStockRepo.findByWarehouseId(warehouseId);
        List<WarehouseStock> lowStockItems = stocks.stream()
                .filter(s -> (s.getQuantity() != null ? s.getQuantity() : 0L) < threshold)
                .sorted(Comparator.comparingLong(s -> s.getQuantity() != null ? s.getQuantity() : 0L))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lowStockItems);
    }

    /**
     * Live warehouse activity feed (recent actions: inward, put-away, transfer,
     * picking, dispatch, returns) For now, returns recent stock transfers as a
     * placeholder. Extend with more activity types as needed.
     */
    @GetMapping("/activity/{warehouseId}")
    public ResponseEntity<List<Map<String, Object>>> getWarehouseActivityFeed(@PathVariable Long warehouseId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        List<StockTransfer> recentTransfers = stockTransferRepo.findByTargetWarehouseIdOrderByDateDesc(warehouseId);
        List<Map<String, Object>> activityFeed = new ArrayList<>();
        LocalDate from = fromDate != null ? LocalDate.parse(fromDate) : null;
        LocalDate to = toDate != null ? LocalDate.parse(toDate) : null;
        int count = 0;
        for (StockTransfer transfer : recentTransfers) {
            if (from != null && transfer.getDate() != null && transfer.getDate().toLocalDate().isBefore(from)) {
                continue;
            }
            if (to != null && transfer.getDate() != null && transfer.getDate().toLocalDate().isAfter(to)) {
                continue;
            }
            Map<String, Object> activity = new HashMap<>();
            activity.put("id", transfer.getId());
            activity.put("date", transfer.getDate());
            activity.put("referenceNumber", transfer.getReferenceNumber());
            activity.put("status", transfer.getStatus());
            activity.put("type", transfer.getTransferType());
            activity.put("itemCount", transfer.getStockTransferItems() != null ? transfer.getStockTransferItems().size() : 0);
            activityFeed.add(activity);
            count++;
            if (count >= limit) {
                break;
            }
        }
        return ResponseEntity.ok(activityFeed);
    }
}
