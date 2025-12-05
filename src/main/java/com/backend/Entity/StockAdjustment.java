package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class StockAdjustment {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessLocation;
    
    private String referenceNumber;
    
    private Date date;
    
    private String adjustmentType;
    
    private BigDecimal totalAmount;
    
    private Long totalUnits;
    
    private BigDecimal amountRecovered;
    
    private String reason;
    
    @OneToMany(mappedBy = "stockAdjustment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockAdjustmentItems> stockAdjustmentItems;
    
    @OneToMany(mappedBy = "stockAdjustment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockTransaction> stockTransaction ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessLocation() {
		return businessLocation;
	}

	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Long totalUnits) {
		this.totalUnits = totalUnits;
	}

	public BigDecimal getAmountRecovered() {
		return amountRecovered;
	}

	public void setAmountRecovered(BigDecimal amountRecovered) {
		this.amountRecovered = amountRecovered;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<StockAdjustmentItems> getStockAdjustmentItems() {
		return stockAdjustmentItems;
	}

	public void setStockAdjustmentItems(List<StockAdjustmentItems> stockAdjustmentItems) {
		this.stockAdjustmentItems = stockAdjustmentItems;
	}

	public List<StockTransaction> getStockTransaction() {
		return stockTransaction;
	}

	public void setStockTransaction(List<StockTransaction> stockTransaction) {
		this.stockTransaction = stockTransaction;
	}


    
    


    
    

}

