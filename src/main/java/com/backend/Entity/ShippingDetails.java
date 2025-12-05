package com.backend.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class ShippingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingDetails;
    private BigDecimal shippingCharges;

   
    @ElementCollection
    @CollectionTable(name = "additional_expenses", joinColumns = @JoinColumn(name = "shipping_details_id"))
    private List<String> additionalExpensesName;

    @ElementCollection
    @CollectionTable(name = "additional_expenses_amount", joinColumns = @JoinColumn(name = "shipping_details_id"))
    private List<BigDecimal> amount;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

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

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}


	
	
}

   
    
    
