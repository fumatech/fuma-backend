package com.backend.Entity.reports;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement_log")
public class StockMovementLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long warehouseId;
    private Long productId;
    private String movementType; // INWARD, DISPATCH, PUTAWAY, TRANSFER, ADJUSTMENT, RETURN
    private Integer quantity;
    private LocalDateTime movementDate;
    private String reference;
    private String remarks;

    // Getters and Setters
}
