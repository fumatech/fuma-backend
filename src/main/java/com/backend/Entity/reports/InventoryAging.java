package com.backend.Entity.reports;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_aging")
public class InventoryAging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long warehouseId;
    private Long productId;
    private LocalDate batchDate;
    private Integer quantity;
    private Integer agingDays;

    // Getters and Setters
}
