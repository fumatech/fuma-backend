package com.backend.Entity.warehouse_transfer;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_transfer_approval")
public class WarehouseTransferApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false)
    private WarehouseTransferMaster transfer;

    @Column(nullable = false)
    private String action; // APPROVED/REJECTED

    @Column(nullable = false)
    private Long actionByUserId;

    @Column(nullable = false)
    private LocalDateTime actionAt;

    private String remarks;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarehouseTransferMaster getTransfer() {
        return transfer;
    }

    public void setTransfer(WarehouseTransferMaster transfer) {
        this.transfer = transfer;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getActionByUserId() {
        return actionByUserId;
    }

    public void setActionByUserId(Long actionByUserId) {
        this.actionByUserId = actionByUserId;
    }

    public LocalDateTime getActionAt() {
        return actionAt;
    }

    public void setActionAt(LocalDateTime actionAt) {
        this.actionAt = actionAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
