package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class SaleDIOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String saleDIOrderId;

	private String franchise;

	private String referenceNumber;

	private String addedBy;

	private Date saleDate;

	private Long customerId;

	private Long payTermNumber;

	private String payTermType;

	private String location;

	private Long totalItems;

	private BigDecimal netTotalAmount;

	private String discountType;

	private BigDecimal discountAmount;

	private String purchaseTax;

	private BigDecimal taxAmount;

	private String additionalNotes;

	@OneToMany(mappedBy = "saleDIOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleDIItem> saleDIItem;

	@OneToMany(mappedBy = "saleDIOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShippingSaleDIDetails> shippingSaleDIDetails;

	@OneToMany(mappedBy = "saleDIOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<StockTransaction> stockTransactions;

	@Transient
	private com.backend.Service.IdGenerator idGenerator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSaleDIOrderId() {
		return saleDIOrderId;
	}

	public void setSaleDIOrderId(String saleDIOrderId) {
		this.saleDIOrderId = saleDIOrderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Long getPayTermNumber() {
		return payTermNumber;
	}

	public void setPayTermNumber(Long payTermNumber) {
		this.payTermNumber = payTermNumber;
	}

	public String getPayTermType() {
		return payTermType;
	}

	public void setPayTermType(String payTermType) {
		this.payTermType = payTermType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(String purchaseTax) {
		this.purchaseTax = purchaseTax;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public List<SaleDIItem> getSaleDIItem() {
		return saleDIItem;
	}

	public void setSaleDIItem(List<SaleDIItem> saleDIItem) {
		this.saleDIItem = saleDIItem;
	}

	public List<ShippingSaleDIDetails> getShippingSaleDIDetails() {
		return shippingSaleDIDetails;
	}

	public void setShippingSaleDIDetails(List<ShippingSaleDIDetails> shippingSaleDIDetails) {
		this.shippingSaleDIDetails = shippingSaleDIDetails;
	}

	public void setIdGenerator(com.backend.Service.IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@PrePersist
	private void generateSaleDIOrderId() {
		if (this.saleDIOrderId == null && idGenerator != null) {
			this.saleDIOrderId = idGenerator.generateSaleDIOrderId();
		}
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

}
