package com.backend.Entity;

import java.math.BigDecimal;

public class CategoryWiseReportDTO {

	private String category;
	private Long totalUnitsSold;
	private Long currentStock;
	private BigDecimal totalAmount;

	public CategoryWiseReportDTO(String category, Long totalUnitsSold, Long currentStock, BigDecimal totalAmount) {
		this.category = category;
		this.totalUnitsSold = totalUnitsSold;
		this.currentStock = currentStock;
		this.totalAmount = totalAmount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getTotalUnitsSold() {
		return totalUnitsSold;
	}

	public void setTotalUnitsSold(Long totalUnitsSold) {
		this.totalUnitsSold = totalUnitsSold;
	}

	public Long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Long currentStock) {
		this.currentStock = currentStock;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
