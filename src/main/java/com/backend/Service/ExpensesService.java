package com.backend.Service;

import java.util.List;

import com.backend.Entity.Expenses;

public interface ExpensesService {

	Expenses saveExpense(Expenses expense);

	List<Expenses> getAllExpenses();

	Expenses getExpenseById(Long id);

	Expenses updateExpense(Long id, Expenses expense);

	void deleteExpenseById(Long id);
}
