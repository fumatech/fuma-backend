package com.backend.DTO;

import com.backend.Entity.QuotationStatus;

public class QuotationStatusUpdateDTO {
    private QuotationStatus toStatus;
    private String comment;

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
}
