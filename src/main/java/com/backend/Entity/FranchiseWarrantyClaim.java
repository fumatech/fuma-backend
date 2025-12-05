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
public class FranchiseWarrantyClaim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String vendor;

	private String franchise;

	private String referenceNumber;

	private Date date;

	private Long status;

	private BigDecimal totalAmount;

	private BigDecimal updatedTotalAmount;

	private Long totalUnits;

	private Long updatedTotalUnits;

	private String reason;

	@OneToMany(mappedBy = "franchiseWarrantyClaim", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FranchiseWarrantyClaimItems> franchiseWarrantyClaimItems;

	@OneToMany(mappedBy = "franchiseWarrantyClaim", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockTransaction> stockTransaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getFranchise() {
		return franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getUpdatedTotalAmount() {
		return updatedTotalAmount;
	}

	public void setUpdatedTotalAmount(BigDecimal updatedTotalAmount) {
		this.updatedTotalAmount = updatedTotalAmount;
	}

	public Long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Long totalUnits) {
		this.totalUnits = totalUnits;
	}

	public Long getUpdatedTotalUnits() {
		return updatedTotalUnits;
	}

	public void setUpdatedTotalUnits(Long updatedTotalUnits) {
		this.updatedTotalUnits = updatedTotalUnits;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<FranchiseWarrantyClaimItems> getFranchiseWarrantyClaimItems() {
		return franchiseWarrantyClaimItems;
	}

	public void setFranchiseWarrantyClaimItems(List<FranchiseWarrantyClaimItems> franchiseWarrantyClaimItems) {
		this.franchiseWarrantyClaimItems = franchiseWarrantyClaimItems;
	}

	public List<StockTransaction> getStockTransaction() {
		return stockTransaction;
	}

	public void setStockTransaction(List<StockTransaction> stockTransaction) {
		this.stockTransaction = stockTransaction;
	}

}
