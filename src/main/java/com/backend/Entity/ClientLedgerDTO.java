package com.backend.Entity;

import java.math.BigDecimal;

public class ClientLedgerDTO {

	private String contact; // vendor OR franchise

	private BigDecimal totalPurchase;
	private BigDecimal totalPurchaseReturn; // ✅ NEW

	private BigDecimal totalSale;
	private BigDecimal totalSaleReturn; // ✅ NEW

	private BigDecimal paidAmount;
	private BigDecimal due;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public BigDecimal getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(BigDecimal totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public BigDecimal getTotalPurchaseReturn() {
		return totalPurchaseReturn;
	}

	public void setTotalPurchaseReturn(BigDecimal totalPurchaseReturn) {
		this.totalPurchaseReturn = totalPurchaseReturn;
	}

	public BigDecimal getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(BigDecimal totalSale) {
		this.totalSale = totalSale;
	}

	public BigDecimal getTotalSaleReturn() {
		return totalSaleReturn;
	}

	public void setTotalSaleReturn(BigDecimal totalSaleReturn) {
		this.totalSaleReturn = totalSaleReturn;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getDue() {
		return due;
	}

	public void setDue(BigDecimal due) {
		this.due = due;
	}
}
