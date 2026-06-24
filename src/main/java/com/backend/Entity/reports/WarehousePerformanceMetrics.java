package com.backend.Entity.reports;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "warehouse_performance_metrics")
public class WarehousePerformanceMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long warehouseId;
    private LocalDate snapshotDate;
    private Integer totalDispatches;
    private Double orderFulfillmentRate;
    private Double accuracyRate;
    private Integer totalInward;
    private Integer totalOutward;

    // Getters and Setters
}
