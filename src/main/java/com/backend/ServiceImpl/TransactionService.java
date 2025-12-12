package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Transaction;
import com.backend.Repository.TransactionRepo;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	public List<Transaction> getPurchaseList() {
		return transactionRepo.findByType("purchase");
	}

	public List<Transaction> getSaleList() {
		return transactionRepo.findByType("sale");
	}

	public List<Transaction> getExpenseList() {
		return transactionRepo.findByType("expense");
	}

	public List<Transaction> getDepositList() {
		return transactionRepo.findByType("deposit");
	}

	public List<Transaction> getRefundList() {
		return transactionRepo.findByType("refund");
	}

	public List<Transaction> getPaymentList() {
		return transactionRepo.findByType("payment");
	}

	public List<Transaction> getCreditNoteList() {
		return transactionRepo.findByType("credit note");
	}

	public List<Transaction> getOpeningBalanceList() {
		return transactionRepo.findByType("opening_balance");
	}
}
