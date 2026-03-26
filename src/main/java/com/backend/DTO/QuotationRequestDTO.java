package com.backend.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuotationRequestDTO {
    private Long customerId;
    private LocalDate issueDate;
    private LocalDate validTill;
    private String currency;
    private String reference;
    private String subject;
    private String notes;
    private String terms;
    private String internalRemarks;
    private List<QuotationItemRequestDTO> items = new ArrayList<>();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getInternalRemarks() {
        return internalRemarks;
    }

    public void setInternalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
    }

    public List<QuotationItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<QuotationItemRequestDTO> items) {
        this.items = items;
    }
}
