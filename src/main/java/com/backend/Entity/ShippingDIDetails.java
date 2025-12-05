package com.backend.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ShippingDIDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingDetails;
    private BigDecimal shippingCharges;

   
    @ElementCollection
    @CollectionTable(name = "additional_DI_expenses", joinColumns = @JoinColumn(name = "shipping_DI_details_id"))
    private List<String> additionalExpensesName;

    @ElementCollection
    @CollectionTable(name = "additional_DI_expenses_amount", joinColumns = @JoinColumn(name = "shipping_DI_details_id"))
    private List<BigDecimal> amount;

    @ManyToOne
    @JoinColumn(name = "purchase_DI_Order_id")
    @JsonBackReference
    private PurchaseDIOrder purchaseDIOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public BigDecimal getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(BigDecimal shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public List<String> getAdditionalExpensesName() {
		return additionalExpensesName;
	}

	public void setAdditionalExpensesName(List<String> additionalExpensesName) {
		this.additionalExpensesName = additionalExpensesName;
	}

	public List<BigDecimal> getAmount() {
		return amount;
	}

	public void setAmount(List<BigDecimal> amount) {
		this.amount = amount;
	}

	public PurchaseDIOrder getPurchaseDIOrder() {
		return purchaseDIOrder;
	}

	public void setPurchaseDIOrder(PurchaseDIOrder purchaseDIOrder) {
		this.purchaseDIOrder = purchaseDIOrder;
	}



	
	
}

   
    
    
