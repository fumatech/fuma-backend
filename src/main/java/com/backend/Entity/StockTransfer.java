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
public class StockTransfer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date date;

	private String referenceNumber;

	private String status;

	private Long locationFrom;

	private Long locationTo;

	private BigDecimal shippingCharges;

	private BigDecimal totalAmount;

	private String note;

	@OneToMany(mappedBy = "stockTransfer", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<StockTransferItems> stockTransferItems;

	@OneToMany(mappedBy = "stockTransfer", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<StockTransaction> stockTransactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLocationFrom() {
		return locationFrom;
	}

	public void setLocationFrom(Long locationFrom) {
		this.locationFrom = locationFrom;
	}

	public Long getLocationTo() {
		return locationTo;
	}

	public void setLocationTo(Long locationTo) {
		this.locationTo = locationTo;
	}

	public BigDecimal getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(BigDecimal shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<StockTransferItems> getStockTransferItems() {
		return stockTransferItems;
	}

	public void setStockTransferItems(List<StockTransferItems> stockTransferItems) {
		this.stockTransferItems = stockTransferItems;
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

}
