package com.backend.dto.reports;

import java.time.LocalDateTime;

public class MovementLogDTO {
    private Long id;
    private String movementType;
    private Integer quantity;
    private LocalDateTime movementDate;
    private String reference;
    private String remarks;
    // Getters and Setters
}
