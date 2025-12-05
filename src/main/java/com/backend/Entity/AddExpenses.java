package com.backend.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AddExpenses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String businessLocation;

	private Long expenseCategory;

	private Long subCategory;

	private LocalDateTime date;

	private Long expenseFor;

	private Long expenseForContact;

	private String file;

	private Long tax;

	private BigDecimal totalAmount;

	private String note;

	@OneToMany(mappedBy = "addExpenses", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transaction = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessLocation() {
		return businessLocation;
	}

	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}

	public Long getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(Long expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public Long getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Long subCategory) {
		this.subCategory = subCategory;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getExpenseFor() {
		return expenseFor;
	}

	public void setExpenseFor(Long expenseFor) {
		this.expenseFor = expenseFor;
	}

	public Long getExpenseForContact() {
		return expenseForContact;
	}

	public void setExpenseForContact(Long expenseForContact) {
		this.expenseForContact = expenseForContact;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

}
