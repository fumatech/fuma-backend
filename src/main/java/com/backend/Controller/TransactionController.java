package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Transaction;
import com.backend.ServiceImpl.TransactionService;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@GetMapping("/purchase")
	public List<Transaction> getPurchase() {
		return service.getPurchaseList();
	}

	@GetMapping("/sale")
	public List<Transaction> getSale() {
		return service.getSaleList();
	}

	@GetMapping("/expense")
	public List<Transaction> getExpense() {
		return service.getExpenseList();
	}

	@GetMapping("/deposit")
	public List<Transaction> getDeposit() {
		return service.getDepositList();
	}

	@GetMapping("/refund")
	public List<Transaction> getRefund() {
		return service.getRefundList();
	}

	@GetMapping("/payment")
	public List<Transaction> getPayment() {
		return service.getPaymentList();
	}

	@GetMapping("/credit-note")
	public List<Transaction> getCreditNote() {
		return service.getCreditNoteList();
	}

	@GetMapping("/opening-balance")
	public List<Transaction> getOpeningBalance() {
		return service.getOpeningBalanceList();
	}
}
