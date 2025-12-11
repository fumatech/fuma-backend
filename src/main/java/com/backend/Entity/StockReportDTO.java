package com.backend.Entity;

import java.math.BigDecimal;

public class StockReportDTO {

	private Long productId;
	private Long variationId;

	private String productName;
	private String sku;
	private String variationName;
	private String variationValue;
	private String category;
	private String businessLocation;

	private BigDecimal unitSellingPrice;
	private BigDecimal defaultPurchasePrice;

	private int totalPurchased;
	private int totalSold;
	private int totalAdjusted;
	private int currentStock;

	private BigDecimal currentStockValueByPurchase;
	private BigDecimal currentStockValueBySale;
	private BigDecimal potentialProfit;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getVariationId() {
		return variationId;
	}

	public void setVariationId(Long variationId) {
		this.variationId = variationId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBusinessLocation() {
		return businessLocation;
	}

	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}

	public BigDecimal getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public BigDecimal getDefaultPurchasePrice() {
		return defaultPurchasePrice;
	}

	public void setDefaultPurchasePrice(BigDecimal defaultPurchasePrice) {
		this.defaultPurchasePrice = defaultPurchasePrice;
	}

	public int getTotalPurchased() {
		return totalPurchased;
	}

	public void setTotalPurchased(int totalPurchased) {
		this.totalPurchased = totalPurchased;
	}

	public int getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}

	public int getTotalAdjusted() {
		return totalAdjusted;
	}

	public void setTotalAdjusted(int totalAdjusted) {
		this.totalAdjusted = totalAdjusted;
	}

	public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	public BigDecimal getCurrentStockValueByPurchase() {
		return currentStockValueByPurchase;
	}

	public void setCurrentStockValueByPurchase(BigDecimal currentStockValueByPurchase) {
		this.currentStockValueByPurchase = currentStockValueByPurchase;
	}

	public BigDecimal getCurrentStockValueBySale() {
		return currentStockValueBySale;
	}

	public void setCurrentStockValueBySale(BigDecimal currentStockValueBySale) {
		this.currentStockValueBySale = currentStockValueBySale;
	}

	public BigDecimal getPotentialProfit() {
		return potentialProfit;
	}

	public void setPotentialProfit(BigDecimal potentialProfit) {
		this.potentialProfit = potentialProfit;
	}

}
