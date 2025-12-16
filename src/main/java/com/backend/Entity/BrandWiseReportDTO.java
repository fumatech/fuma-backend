package com.backend.Entity;

import java.math.BigDecimal;

public class BrandWiseReportDTO {

	private String brand;
	private Long totalUnitsSold;
	private Long currentStock;
	private BigDecimal totalAmount;

	public BrandWiseReportDTO(String brand, Long totalUnitsSold, Long currentStock, BigDecimal totalAmount) {
		this.brand = brand;
		this.totalUnitsSold = totalUnitsSold;
		this.currentStock = currentStock;
		this.totalAmount = totalAmount;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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
