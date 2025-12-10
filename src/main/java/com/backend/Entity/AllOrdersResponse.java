package com.backend.Entity;

import java.util.List;

public class AllOrdersResponse {

	private List<PurchasePoOrder> purchasePoOrders;
	private List<PurchaseDIOrder> purchaseDiOrders;
	private List<SaleSoOrder> saleSoOrders;
	private List<SaleDIOrder> saleDiOrders;
	private List<Transaction> transaction;

	public List<PurchasePoOrder> getPurchasePoOrders() {
		return purchasePoOrders;
	}

	public void setPurchasePoOrders(List<PurchasePoOrder> purchasePoOrders) {
		this.purchasePoOrders = purchasePoOrders;
	}

	public List<PurchaseDIOrder> getPurchaseDiOrders() {
		return purchaseDiOrders;
	}

	public void setPurchaseDiOrders(List<PurchaseDIOrder> purchaseDiOrders) {
		this.purchaseDiOrders = purchaseDiOrders;
	}

	public List<SaleSoOrder> getSaleSoOrders() {
		return saleSoOrders;
	}

	public void setSaleSoOrders(List<SaleSoOrder> saleSoOrders) {
		this.saleSoOrders = saleSoOrders;
	}

	public List<SaleDIOrder> getSaleDiOrders() {
		return saleDiOrders;
	}

	public void setSaleDiOrders(List<SaleDIOrder> saleDiOrders) {
		this.saleDiOrders = saleDiOrders;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

}
