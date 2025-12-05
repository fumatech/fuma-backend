package com.backend.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String paymentMethod;
	private BigDecimal amount;
	private String transactionType;
	private String addedBy;
	private String note;

	private LocalDateTime date;
	private BigDecimal balance;
	private String vendor;
	private String franchiseName;
	private String chequeNumber;
	private String cardType;
	private String cardNumber;
	private String cardHolderName;
	private String cardTransactionNumber;

	private Long cardMonth;

	private Long cardYear;

	private Long cardSecurity;

	private String cashDetails;

	@ManyToOne
	@JoinColumn(name = "add_Expenses_id")
	@JsonBackReference
	private AddExpenses addExpenses;

	@ManyToOne
	@JoinColumn(name = "purchase_Po_Order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchasePoOrder purchasePoOrder;

	@ManyToOne
	@JoinColumn(name = "purchase_DI_Order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseDIOrder purchaseDIOrder;

//    @ManyToOne
//    @JoinColumn(name = "sale_So_Order_id") 
//    @JsonBackReference
//    @JsonIgnore
//    private SaleSoOrder saleSoOrder;

	@ManyToOne
	@JoinColumn(name = "sale_DI_Order_id")
	@JsonBackReference
	@JsonIgnore
	private SaleDIOrder saleDIOrder;

	@ManyToOne
	@JoinColumn(name = "payment_account_id", nullable = false)
	@JsonBackReference
	@JsonIgnore
	private PaymentAccount paymentAccount;

	// ✅ **Add this transient field to store `paymentAccountId` from JSON request**
	@Transient
	private Long paymentAccountId;

	// Getters and setters
	public Long getPaymentAccountId() {
		if (paymentAccountId == null && paymentAccount != null) {
			return paymentAccount.getId();
		}
		return paymentAccountId;
	}

	public void setPaymentAccountId(Long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}
	@Transient
	public BigDecimal getDebit() {
	    if (transactionType == null) return BigDecimal.ZERO;

	    switch (transactionType.toLowerCase()) {
	        case "opening_balance":
	        case "deposit":
	        case "credit note":
	            return amount != null ? amount : BigDecimal.ZERO;
	        default:
	            return BigDecimal.ZERO;
	    }
	}

	@Transient
	public BigDecimal getCredit() {
	    if (transactionType == null) return BigDecimal.ZERO;

	    switch (transactionType.toLowerCase()) {
	        case "sale":
	        case "payment":
	        case "expense":
	            return amount != null ? amount : BigDecimal.ZERO;
	        default:
	            return BigDecimal.ZERO;
	    }
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardTransactionNumber() {
		return cardTransactionNumber;
	}

	public void setCardTransactionNumber(String cardTransactionNumber) {
		this.cardTransactionNumber = cardTransactionNumber;
	}

	public Long getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(Long cardMonth) {
		this.cardMonth = cardMonth;
	}

	public Long getCardYear() {
		return cardYear;
	}

	public void setCardYear(Long cardYear) {
		this.cardYear = cardYear;
	}

	public Long getCardSecurity() {
		return cardSecurity;
	}

	public void setCardSecurity(Long cardSecurity) {
		this.cardSecurity = cardSecurity;
	}

	public String getCashDetails() {
		return cashDetails;
	}

	public void setCashDetails(String cashDetails) {
		this.cashDetails = cashDetails;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
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

//	public SaleSoOrder getSaleSoOrder() {
//		return saleSoOrder;
//	}
//
//	public void setSaleSoOrder(SaleSoOrder saleSoOrder) {
//		this.saleSoOrder = saleSoOrder;
//	}

	public SaleDIOrder getSaleDIOrder() {
		return saleDIOrder;
	}

	public AddExpenses getAddExpenses() {
		return addExpenses;
	}

	public void setAddExpenses(AddExpenses addExpenses) {
		this.addExpenses = addExpenses;
	}

	public void setSaleDIOrder(SaleDIOrder saleDIOrder) {
		this.saleDIOrder = saleDIOrder;
	}

}
