package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PurchasePoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String purchasePoOrderId; 
    
    private Long status;
    
    private String vendor;

    private String addedBy;
     
    private String orderedBy;
 
    private String referenceNumber;
    
    private String purchaseReferenceNumber;

    private Date orderDate;
    
    private Date purchaseDate;
 
    private Long payTermNumber;
    
    private String payTermType;

    private String location;

    private String file;

    private Long totalItems;
    
    private BigDecimal netTotalAmount;

    private String discountType;
    
    private BigDecimal discountAmount;
    
    private String purchaseTax;

    private BigDecimal taxAmount;
  
    private String additionalNotes;

    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "purchasePoOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasePoItem> purchasePoItem = new ArrayList<>();
    
    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "purchasePoOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transaction = new ArrayList<>();

    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "purchasePoOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingPoDetails> shippingPoDetails = new ArrayList<>();
    

    @OneToMany(mappedBy = "purchasePoOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockTransaction> stockTransactions = new ArrayList<>();
    

    // Getters and Setters
    public Long getId() {
        return id;        
    }

    public void setId(Long id) {
        this.id = id;
    }

 



	
	

	public String getPurchasePoOrderId() {
		return purchasePoOrderId;
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
    
    

    public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getPurchaseReferenceNumber() {
		return purchaseReferenceNumber;
	}

	public void setPurchaseReferenceNumber(String purchaseReferenceNumber) {
		this.purchaseReferenceNumber = purchaseReferenceNumber;
	}

	public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
    

    public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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
    
    

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    
    
    

 

	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}



	public String getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(String purchaseTax) {
		this.purchaseTax = purchaseTax;
	}



	public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

	public List<PurchasePoItem> getPurchasePoItem() {
		return purchasePoItem;
	}

	public void setPurchasePoItem(List<PurchasePoItem> purchasePoItem) {
		this.purchasePoItem = purchasePoItem;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public List<ShippingPoDetails> getShippingPoDetails() {
		return shippingPoDetails;
	}

	public void setShippingPoDetails(List<ShippingPoDetails> shippingPoDetails) {
		this.shippingPoDetails = shippingPoDetails;
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

	public void setPurchasePoOrderId(String purchasePoOrderId) {
		this.purchasePoOrderId = purchasePoOrderId;
	}

	
    
    

 
    
}