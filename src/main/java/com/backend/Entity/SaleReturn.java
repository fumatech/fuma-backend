package com.backend.Entity;

import java.sql.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class SaleReturn {
	
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String purchaseReturnId;
	    
	    private Long status;

	    private String vendor;
	   
	    private String addedBy;

	    private String referenceNumber;

	    private Date orderDate;

	    private String location;
	      
	    private Long totalItems;
	    
	    private Long totalShippedItems;

	    private String additionalNotes;

	    
	    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = false)
	    private List<SaleReturnItems> saleReturnItems;
	    
	    
	    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = false)
	    private List<StockTransaction> stockTransactions;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getPurchaseReturnId() {
			return purchaseReturnId;
		}


		public void setPurchaseReturnId(String purchaseReturnId) {
			this.purchaseReturnId = purchaseReturnId;
		}


		public Long getStatus() {
			return status;
		}


		public void setStatus(Long status) {
			this.status = status;
		}


		public String getVendor() {
			return vendor;
		}


		public void setVendor(String vendor) {
			this.vendor = vendor;
		}


		public String getAddedBy() {
			return addedBy;
		}


		public void setAddedBy(String addedBy) {
			this.addedBy = addedBy;
		}


		public String getReferenceNumber() {
			return referenceNumber;
		}


		public void setReferenceNumber(String referenceNumber) {
			this.referenceNumber = referenceNumber;
		}


		public Date getOrderDate() {
			return orderDate;
		}


		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
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


		public Long getTotalShippedItems() {
			return totalShippedItems;
		}


		public void setTotalShippedItems(Long totalShippedItems) {
			this.totalShippedItems = totalShippedItems;
		}


		public String getAdditionalNotes() {
			return additionalNotes;
		}


		public void setAdditionalNotes(String additionalNotes) {
			this.additionalNotes = additionalNotes;
		}


		public List<SaleReturnItems> getSaleReturnItems() {
			return saleReturnItems;
		}


		public void setSaleReturnItems(List<SaleReturnItems> saleReturnItems) {
			this.saleReturnItems = saleReturnItems;
		}


		public List<StockTransaction> getStockTransactions() {
			return stockTransactions;
		}


		public void setStockTransactions(List<StockTransaction> stockTransactions) {
			this.stockTransactions = stockTransactions;
		}



	    
	    

}
