package com.backend.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class ProductPurchaseReportDTO {

	private String productName;
	private String sku;
	private String supplier;
	private String referenceNumber;
	private Date date;
	private Long quantity;
	private BigDecimal unitPurchasePrice;
	private BigDecimal subtotal;
	private Long totalUnitAdjusted;
	private String variationName;
	private String variationValue;

	public ProductPurchaseReportDTO(String productName, String sku, String supplier, String referenceNumber, Date date,
			Long quantity, BigDecimal unitPurchasePrice, BigDecimal subtotal, Long totalUnitAdjusted,
			String variationName, String variationValue) {
		this.productName = productName;
		this.sku = sku;
		this.supplier = supplier;
		this.referenceNumber = referenceNumber;
		this.date = date;
		this.quantity = quantity;
		this.unitPurchasePrice = unitPurchasePrice;
		this.subtotal = subtotal;
		this.totalUnitAdjusted = totalUnitAdjusted;
		this.variationName = variationName;
		this.variationValue = variationValue;
	}

	// Getters & Setters
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(BigDecimal unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Long getTotalUnitAdjusted() {
		return totalUnitAdjusted;
	}

	public void setTotalUnitAdjusted(Long totalUnitAdjusted) {
		this.totalUnitAdjusted = totalUnitAdjusted;
	}

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public String getVariationValue() {
		return variationValue;
	}

	public void setVariationValue(String variationValue) {
		this.variationValue = variationValue;
	}

}
