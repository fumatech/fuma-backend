package com.backend.Entity;

import java.math.BigDecimal;

public class PurchaseSaleSummaryDTO {

	// Purchases
	private BigDecimal totalPurchase = BigDecimal.ZERO;
	private BigDecimal purchaseIncludingTax = BigDecimal.ZERO;
	private BigDecimal totalPurchaseReturnIncludingTax = BigDecimal.ZERO;
	private BigDecimal purchaseDue = BigDecimal.ZERO;

	// Sales
	private BigDecimal totalSale = BigDecimal.ZERO;
	private BigDecimal saleIncludingTax = BigDecimal.ZERO;
	private BigDecimal totalSaleReturnIncludingTax = BigDecimal.ZERO;
	private BigDecimal saleDue = BigDecimal.ZERO;

	// Overall
	private BigDecimal saleMinusPurchase = BigDecimal.ZERO;
	private BigDecimal dueAmount = BigDecimal.ZERO;

	public BigDecimal getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(BigDecimal totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public BigDecimal getPurchaseIncludingTax() {
		return purchaseIncludingTax;
	}

	public void setPurchaseIncludingTax(BigDecimal purchaseIncludingTax) {
		this.purchaseIncludingTax = purchaseIncludingTax;
	}

	public BigDecimal getTotalPurchaseReturnIncludingTax() {
		return totalPurchaseReturnIncludingTax;
	}

	public void setTotalPurchaseReturnIncludingTax(BigDecimal totalPurchaseReturnIncludingTax) {
		this.totalPurchaseReturnIncludingTax = totalPurchaseReturnIncludingTax;
	}

	public BigDecimal getPurchaseDue() {
		return purchaseDue;
	}

	public void setPurchaseDue(BigDecimal purchaseDue) {
		this.purchaseDue = purchaseDue;
	}

	public BigDecimal getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(BigDecimal totalSale) {
		this.totalSale = totalSale;
	}

	public BigDecimal getSaleIncludingTax() {
		return saleIncludingTax;
	}

	public void setSaleIncludingTax(BigDecimal saleIncludingTax) {
		this.saleIncludingTax = saleIncludingTax;
	}

	public BigDecimal getTotalSaleReturnIncludingTax() {
		return totalSaleReturnIncludingTax;
	}

	public void setTotalSaleReturnIncludingTax(BigDecimal totalSaleReturnIncludingTax) {
		this.totalSaleReturnIncludingTax = totalSaleReturnIncludingTax;
	}

	public BigDecimal getSaleDue() {
		return saleDue;
	}

	public void setSaleDue(BigDecimal saleDue) {
		this.saleDue = saleDue;
	}

	public BigDecimal getSaleMinusPurchase() {
		return saleMinusPurchase;
	}

	public void setSaleMinusPurchase(BigDecimal saleMinusPurchase) {
		this.saleMinusPurchase = saleMinusPurchase;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		this.dueAmount = dueAmount;
	}

}
