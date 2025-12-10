package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.AddExpenses;
import com.backend.Entity.PaymentAccount;
import com.backend.Entity.Transaction;
import com.backend.Repository.PaymentAccountRepo;
import com.backend.Service.AddExpensesService;
import com.backend.Util.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/add-expenses")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class AddExpensesController {
	@Autowired
	private AddExpensesService addExpensesService;
	@Autowired
	private FileUploadUtil fileUploadUtil;
	@Autowired
	private PaymentAccountRepo paymentAccountRepo;

	@PostMapping("/save")
	public ResponseEntity<AddExpenses> createExpense(@RequestParam("addExpenses") String addExpensesJson,
			@RequestParam("transaction") String transactionJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();

			// 🔹 Convert JSON → Java objects
			AddExpenses addExpenses = mapper.readValue(addExpensesJson, AddExpenses.class);
			Transaction transaction = mapper.readValue(transactionJson, Transaction.class);

			// 🔹 Handle file upload
			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				addExpenses.setFile(filePath);
			}

			// 🔹 Set transaction under AddExpenses
			transaction.setAddExpenses(addExpenses);

			// 🔹 Set PaymentAccount
			if (transaction.getPaymentAccountId() != null) {
				PaymentAccount account = paymentAccountRepo.findById(transaction.getPaymentAccountId())
						.orElseThrow(() -> new RuntimeException("Payment Account not found"));

				transaction.setPaymentAccount(account);
			}

			// 🔹 Add transaction to list
			addExpenses.getTransaction().add(transaction);

			// 🔹 Save Expenses + Transaction in one shot
			AddExpenses saved = addExpensesService.saveAddExpenses(addExpenses);

			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<AddExpenses> updateExpense(@PathVariable Long id,
			@RequestParam("addExpenses") String addExpensesJson, @RequestParam("transaction") String transactionJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		try {

			AddExpenses existing = addExpensesService.getAddExpensesById(id)
					.orElseThrow(() -> new RuntimeException("Expense not found"));

			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();

			// Convert JSON → objects
			AddExpenses updatedExpense = mapper.readValue(addExpensesJson, AddExpenses.class);
			updatedExpense.setId(id);

			// Handle file
			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				updatedExpense.setFile(filePath);
			} else {
				updatedExpense.setFile(existing.getFile());
			}

			// Convert transaction JSON
			Transaction updatedTx = mapper.readValue(transactionJson, Transaction.class);

			// ------------ IMPORTANT LOGIC ------------
			// Keep OLD TRANSACTION ID
			if (!existing.getTransaction().isEmpty()) {
				Long oldTxId = existing.getTransaction().get(0).getId();
				updatedTx.setId(oldTxId);
			}
			// ------------------------------------------

			// Set Payment Account
			if (updatedTx.getPaymentAccountId() != null) {
				PaymentAccount acc = paymentAccountRepo.findById(updatedTx.getPaymentAccountId())
						.orElseThrow(() -> new RuntimeException("Payment Account not found"));
				updatedTx.setPaymentAccount(acc);
			}

			// Reconnect transaction with updated expense
			updatedTx.setAddExpenses(updatedExpense);

			// Replace existing transaction (UPDATE not INSERT)
			updatedExpense.getTransaction().clear();
			updatedExpense.getTransaction().add(updatedTx);

			AddExpenses saved = addExpensesService.updateAddExpenses(id, updatedExpense);
			return new ResponseEntity<>(saved, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<AddExpenses>> getAllExpenses() {
		return new ResponseEntity<>(addExpensesService.getAllAddExpenses(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<AddExpenses> getExpenseById(@PathVariable Long id) {
		return addExpensesService.getAddExpensesById(id).map(exp -> new ResponseEntity<>(exp, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		addExpensesService.deleteAddExpenses(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getall-with-tax")
	public ResponseEntity<List<AddExpenses>> getAllExpensesWithTax() {
		return new ResponseEntity<>(addExpensesService.getAllAddExpensesWithTax(), HttpStatus.OK);
	}

}