package com.backend.DTO;

import java.time.LocalDateTime;

import com.backend.Entity.QuotationStatus;

public class QuotationHistoryDTO {
    private QuotationStatus fromStatus;
    private QuotationStatus toStatus;
    private String comment;
    private String changedBy;
    private LocalDateTime changedAt;

    public QuotationStatus getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(QuotationStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public QuotationStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(QuotationStatus toStatus) {
        this.toStatus = toStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
