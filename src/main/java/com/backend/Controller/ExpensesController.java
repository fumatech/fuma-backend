package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Expenses;
import com.backend.Service.ExpensesService;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ExpensesController {

	@Autowired
	private ExpensesService expensesService;

	// ✅ Create new expense
	@PostMapping("/save")
	public ResponseEntity<Expenses> saveExpense(@RequestBody Expenses expense) {
		Expenses savedExpense = expensesService.saveExpense(expense);
		return ResponseEntity.ok(savedExpense);
	}

	// ✅ Get all expenses
	@GetMapping("/getall")
	public ResponseEntity<List<Expenses>> getAllExpenses() {
		return ResponseEntity.ok(expensesService.getAllExpenses());
	}

	// ✅ Get expense by ID
	@GetMapping("/get/{id}")
	public ResponseEntity<Expenses> getExpenseById(@PathVariable Long id) {
		Expenses expense = expensesService.getExpenseById(id);
		return (expense != null) ? ResponseEntity.ok(expense) : ResponseEntity.notFound().build();
	}

	// ✅ Update expense
	@PutMapping("/update/{id}")
	public ResponseEntity<Expenses> updateExpense(@PathVariable Long id, @RequestBody Expenses expense) {
		Expenses updatedExpense = expensesService.updateExpense(id, expense);
		return (updatedExpense != null) ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
	}

	// ✅ Delete expense
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		expensesService.deleteExpenseById(id);
		return ResponseEntity.noContent().build();
	}
}
