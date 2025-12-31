package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StockTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId; // Foreign key to Product
	private Long variationId; // Foreign key to Variation

	private int quantity;
	private String transactionType; // e.g., "purchase", "sale", "adjustment"

	private Date date;

	private BigDecimal price;
	private String note;

	// Many-to-one relationship with PurchasePoOrder
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_po_order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchasePoOrder purchasePoOrder;

	// Many-to-one relationship with PurchasePoOrder
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_di_order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseDIOrder purchaseDIOrder;

	@ManyToOne
	@JoinColumn(name = "purchase_return_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseReturn purchaseReturn;

	@ManyToOne
	@JoinColumn(name = "sale_So_Order_id")
	@JsonBackReference
	@JsonIgnore
	private SaleSoOrder saleSoOrder;

	@ManyToOne
	@JoinColumn(name = "sale_DI_Order_id")
	@JsonBackReference
	@JsonIgnore
	private SaleDIOrder saleDIOrder;

	@ManyToOne
	@JoinColumn(name = "stock_Adjustment_id")
	@JsonBackReference
	@JsonIgnore
	private StockAdjustment stockAdjustment;

	@ManyToOne
	@JoinColumn(name = "sale_return_id")
	@JsonBackReference
	@JsonIgnore
	private SaleReturn saleReturn;

	@ManyToOne
	@JoinColumn(name = "franchise_purchase_return_id")
	@JsonBackReference
	@JsonIgnore
	private FranchisePurchaseReturn franchisePurchaseReturn;

	@ManyToOne
	@JoinColumn(name = "warranty_claim_id")
	@JsonBackReference
	@JsonIgnore
	private WarrantyClaim warrantyClaim;

	@ManyToOne
	@JoinColumn(name = "vendor_warranty_claim_id")
	@JsonBackReference
	@JsonIgnore
	private VendorWarrantyClaim vendorWarrantyClaim;

	@ManyToOne
	@JoinColumn(name = "franchise_warranty_claim_id")
	@JsonBackReference
	@JsonIgnore
	private FranchiseWarrantyClaim franchiseWarrantyClaim;

	@ManyToOne
	@JoinColumn(name = "stock_transfer_id")
	@JsonBackReference
	@JsonIgnore
	private StockTransfer stockTransfer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PurchasePoOrder getPurchasePoOrder() {
		return purchasePoOrder;
	}

	public void setPurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		this.purchasePoOrder = purchasePoOrder;
	}

	public PurchaseDIOrder getPurchaseDIOrder() {
		return purchaseDIOrder;
	}

	public void setPurchaseDIOrder(PurchaseDIOrder purchaseDIOrder) {
		this.purchaseDIOrder = purchaseDIOrder;
	}

	public PurchaseReturn getPurchaseReturn() {
		return purchaseReturn;
	}

	public void setPurchaseReturn(PurchaseReturn purchaseReturn) {
		this.purchaseReturn = purchaseReturn;
	}

	public SaleSoOrder getSaleSoOrder() {
		return saleSoOrder;
	}

	public void setSaleSoOrder(SaleSoOrder saleSoOrder) {
		this.saleSoOrder = saleSoOrder;
	}

	public SaleDIOrder getSaleDIOrder() {
		return saleDIOrder;
	}

	public void setSaleDIOrder(SaleDIOrder saleDIOrder) {
		this.saleDIOrder = saleDIOrder;
	}

	public StockAdjustment getStockAdjustment() {
		return stockAdjustment;
	}

	public void setStockAdjustment(StockAdjustment stockAdjustment) {
		this.stockAdjustment = stockAdjustment;
	}

	public SaleReturn getSaleReturn() {
		return saleReturn;
	}

	public void setSaleReturn(SaleReturn saleReturn) {
		this.saleReturn = saleReturn;
	}

	public WarrantyClaim getWarrantyClaim() {
		return warrantyClaim;
	}

	public void setWarrantyClaim(WarrantyClaim warrantyClaim) {
		this.warrantyClaim = warrantyClaim;
	}

	public VendorWarrantyClaim getVendorWarrantyClaim() {
		return vendorWarrantyClaim;
	}

	public void setVendorWarrantyClaim(VendorWarrantyClaim vendorWarrantyClaim) {
		this.vendorWarrantyClaim = vendorWarrantyClaim;
	}

	public FranchisePurchaseReturn getFranchisePurchaseReturn() {
		return franchisePurchaseReturn;
	}

	public void setFranchisePurchaseReturn(FranchisePurchaseReturn franchisePurchaseReturn) {
		this.franchisePurchaseReturn = franchisePurchaseReturn;
	}

	public FranchiseWarrantyClaim getFranchiseWarrantyClaim() {
		return franchiseWarrantyClaim;
	}

	public void setFranchiseWarrantyClaim(FranchiseWarrantyClaim franchiseWarrantyClaim) {
		this.franchiseWarrantyClaim = franchiseWarrantyClaim;
	}

	public StockTransfer getStockTransfer() {
		return stockTransfer;
	}

	public void setStockTransfer(StockTransfer stockTransfer) {
		this.stockTransfer = stockTransfer;
	}

}
