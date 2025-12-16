package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

public class GroupedDateReportDTO {

	private String productName;
	private String sku;
	private Date saleDate;
	private Long totalUnitsSold;
	private BigDecimal totalAmount;
	private Long currentStock;

	public GroupedDateReportDTO(String productName, String sku, Date saleDate, Long totalUnitsSold,
			BigDecimal totalAmount, Long currentStock) {
		this.productName = productName;
		this.sku = sku;
		this.saleDate = saleDate;
		this.totalUnitsSold = totalUnitsSold;
		this.totalAmount = totalAmount;
		this.currentStock = currentStock;
	}

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

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Long getTotalUnitsSold() {
		return totalUnitsSold;
	}

	public void setTotalUnitsSold(Long totalUnitsSold) {
		this.totalUnitsSold = totalUnitsSold;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Long currentStock) {
		this.currentStock = currentStock;
	}

}
