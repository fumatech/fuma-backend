package com.backend.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expenses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String expenseName;

	private String expenseCode;

	private String description;

	// 🔹 Self-referencing relationship: each expense can have a parent expense
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	private Expenses parentExpense;

	// 🔹 One expense can have many child sub-expenses
	@OneToMany(mappedBy = "parentExpense", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Expenses> subExpenses;

	// ✅ Constructors
	public Expenses() {
	}

	public Expenses(String expenseName, String expenseCode, String description, Expenses parentExpense) {
		this.expenseName = expenseName;
		this.expenseCode = expenseCode;
		this.description = description;
		this.parentExpense = parentExpense;
	}

	// ✅ Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public String getExpenseCode() {
		return expenseCode;
	}

	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Expenses getParentExpense() {
		return parentExpense;
	}

	public void setParentExpense(Expenses parentExpense) {
		this.parentExpense = parentExpense;
	}

	public List<Expenses> getSubExpenses() {
		return subExpenses;
	}

	public void setSubExpenses(List<Expenses> subExpenses) {
		this.subExpenses = subExpenses;
	}
}
