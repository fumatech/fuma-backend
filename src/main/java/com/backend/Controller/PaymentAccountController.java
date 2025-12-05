package com.backend.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.PaymentAccount;
import com.backend.Entity.Transaction;
import com.backend.Service.PaymentAccountService;
import com.backend.ServiceImpl.PaymentAccountServiceImpl;

@RestController
@RequestMapping("/payment-account")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PaymentAccountController {

	@Autowired
	private PaymentAccountService paymentAccountService;

	@Autowired
	private PaymentAccountServiceImpl paymentAccountServiceImpl;

	@PostMapping("/save")
	public ResponseEntity<PaymentAccount> savePaymentAccount(@RequestBody PaymentAccount paymentAccount) {
		PaymentAccount payment = paymentAccountService.savePaymentAccount(paymentAccount);
		return new ResponseEntity<>(payment, HttpStatus.CREATED);
	}

	@PostMapping("transaction/{accountId}")
	public ResponseEntity<Transaction> createTransaction(@PathVariable Long accountId,
			@RequestBody Transaction transactionRequest) {

		try {
			Transaction savedTransaction = paymentAccountService.createTransaction(accountId, transactionRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/balance/{id}")
	public ResponseEntity<BigDecimal> getCurrentBalance(@PathVariable Long id) {
		BigDecimal balance = paymentAccountService.getCurrentBalance(id);

		return ResponseEntity.ok(balance);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<PaymentAccount>> getAllPaymentAccount() {
		List<PaymentAccount> payment = paymentAccountService.getAllPaymentAccounts();
		return new ResponseEntity<>(payment, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<PaymentAccount> getPaymentAccountById(@PathVariable Long id) {
		Optional<PaymentAccount> payment = paymentAccountService.getPaymentAccountById(id);
		return payment.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<PaymentAccount> updatePaymentAccount(@PathVariable Long id,
			@RequestBody PaymentAccount paymentAccount) {
		PaymentAccount updatedAccount = paymentAccountService.updatePaymentAccount(id, paymentAccount);
		if (updatedAccount != null) {
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePaymentAccount(@PathVariable Long id) {
		paymentAccountService.deletePaymentAccount(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/transaction/update/{transactionId}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId,
			@RequestBody Transaction transactionRequest) {

		try {
			Transaction updatedTransaction = paymentAccountService.updateTransaction(transactionId,
					transactionRequest.getAmount(), transactionRequest.getPaymentMethod(),
					transactionRequest.getTransactionType(), transactionRequest.getAddedBy(),
					transactionRequest.getNote(), transactionRequest.getDate(), transactionRequest.getVendor());

			return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update-status/{id}")
	public ResponseEntity<String> updateAccountStatus(@PathVariable Long id, @RequestParam Long status) {
		try {
			paymentAccountService.updateAccountStatus(id, status);
			return ResponseEntity.ok("Account status updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment account not found");
		}
	}

	@GetMapping("/getbyvendor/{vendor}")
	public ResponseEntity<Map<String, List<Object>>> getPaymentByVendor(@PathVariable String vendor) {
		Map<String, List<Object>> orders = paymentAccountServiceImpl.getPaymentByVendor(vendor);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getbyfranchise/{franchiseName}")
	public ResponseEntity<Map<String, List<Object>>> getPaymentByFranchiseName(@PathVariable String franchiseName) {
		Map<String, List<Object>> orders = paymentAccountServiceImpl.getPaymentByFranchiseName(franchiseName);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

}