package com.backend.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ShippingPoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingDetails;
    private BigDecimal shippingCharges;
     
   
    @ElementCollection
    @CollectionTable(name = "additional_Po_expenses", joinColumns = @JoinColumn(name = "shipping_Po_details_id"))
    private List<String> additionalExpensesName;

    @ElementCollection
    @CollectionTable(name = "additional_Po_expenses_amount", joinColumns = @JoinColumn(name = "shipping_Po_details_id"))
    private List<BigDecimal> amount;

    @ManyToOne
    @JoinColumn(name = "purchase_Po_Order_id")
    @JsonBackReference
    private PurchasePoOrder purchasePoOrder;

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

	public PurchasePoOrder getPurchasePoOrder() {
		return purchasePoOrder;
	}

	public void setPurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		this.purchasePoOrder = purchasePoOrder;
	}

	
	
}

   
    
    
