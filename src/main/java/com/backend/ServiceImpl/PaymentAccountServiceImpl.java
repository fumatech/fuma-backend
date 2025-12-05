package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PaymentAccount;
import com.backend.Entity.Transaction;
import com.backend.Repository.PaymentAccountRepo;
import com.backend.Repository.TransactionRepo;
import com.backend.Service.PaymentAccountService;

import jakarta.transaction.Transactional;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService {

	@Autowired
	private PaymentAccountRepo paymentAccountRepo;

	@Autowired
	private TransactionRepo transactionRepo;

	@Transactional
	public Transaction createTransaction(Long accountId, Transaction transactionRequest) {
		// Fetch the payment account
		PaymentAccount paymentAccount = paymentAccountRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		// Set account reference
		transactionRequest.setPaymentAccount(paymentAccount);

		// Get the current balance of the account (default to zero if null)
		BigDecimal previousBalance = paymentAccount.getBalance() != null ? paymentAccount.getBalance()
				: BigDecimal.ZERO;
		BigDecimal newBalance = previousBalance;

		// Determine the new balance based on transaction type
		switch (transactionRequest.getTransactionType().toLowerCase()) {
		case "deposit":
		case "sale":
		case "opening_balance":
			newBalance = previousBalance.add(transactionRequest.getAmount()); // Add amount
			break;
		case "purchase":
		case "refund":
		case "credit note":
		case "payment":
		case "expense":
			newBalance = previousBalance.subtract(transactionRequest.getAmount()); // Subtract amount
			break;
		default:
			throw new RuntimeException("Unknown transaction type: " + transactionRequest.getTransactionType());
		}

		// Set new balance in the transaction
		transactionRequest.setBalance(newBalance);

		// Save the transaction
		Transaction savedTransaction = transactionRepo.save(transactionRequest);

		// Update and save the account balance
		paymentAccount.setBalance(newBalance);
		paymentAccountRepo.save(paymentAccount);

		return savedTransaction;
	}

	@Override
	public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) {
		// Check if there are transactions associated with the account
		if (paymentAccount.getTransactions() != null && !paymentAccount.getTransactions().isEmpty()) {
			// Iterate over the transactions to ensure they are linked to the correct
			// account
			paymentAccount.getTransactions().forEach(transaction -> transaction.setPaymentAccount(paymentAccount));

			// Calculate the balance by iterating over the transactions and adjusting the
			// balance
			BigDecimal balance = BigDecimal.ZERO; // Start with zero balance

			for (Transaction transaction : paymentAccount.getTransactions()) {
				// Add or subtract the amount based on transaction type
				switch (transaction.getTransactionType().toLowerCase()) {
				case "deposit":
				case "sale":
				case "opening_balance":
					balance = balance.add(transaction.getAmount()); // Add for deposit, sale, or opening balance
					break;
				case "purchase":
				case "refund":
				case "credit note":
				case "expense":
					balance = balance.subtract(transaction.getAmount()); // Subtract for purchase or expense
					break;
				default:
					throw new RuntimeException("Unknown transaction type");
				}
				// Update the balance in each transaction after calculation
				transaction.setBalance(balance);
			}

			// Set the calculated balance for the account
			paymentAccount.setBalance(balance);
		} else {
			// If no transactions are provided, set the balance to the current balance of
			// the account
			// If this is a new account, you could initialize the balance here
			if (paymentAccount.getBalance() == null) {
				paymentAccount.setBalance(BigDecimal.ZERO); // Set initial balance to zero if none is provided
			}
		}

		// Save the payment account (whether it's a new account or updated one)
		return paymentAccountRepo.save(paymentAccount);
	}

	@Override
	public List<PaymentAccount> getAllPaymentAccounts() {
		return paymentAccountRepo.findAll();
	}

	@Override
	public Optional<PaymentAccount> getPaymentAccountById(Long id) {
		return paymentAccountRepo.findById(id);
	}

	@Override
	public PaymentAccount updatePaymentAccount(Long id, PaymentAccount paymentAccount) {
		Optional<PaymentAccount> existingAccount = paymentAccountRepo.findById(id);
		if (existingAccount.isPresent()) {
			PaymentAccount account = existingAccount.get();
			account.setAccountName(paymentAccount.getAccountName());
			account.setAccountNumber(paymentAccount.getAccountNumber());
			account.setAccountType(paymentAccount.getAccountType());
			account.setTransactions(paymentAccount.getTransactions());
			return paymentAccountRepo.save(account);
		} else {
			return null; // Handle error appropriately
		}
	}

	@Override
	public void deletePaymentAccount(Long id) {
		paymentAccountRepo.deleteById(id);
	}

	@Override
	public BigDecimal getCurrentBalance(Long id) {
		// Fetch the PaymentAccount object by id
		Optional<PaymentAccount> paymentAccountOpt = paymentAccountRepo.findById(id);

		// If the account doesn't exist, return balance as 0
		if (!paymentAccountOpt.isPresent()) {
			return BigDecimal.ZERO;
		}

		// Extract the payment account from the Optional
		PaymentAccount paymentAccount = paymentAccountOpt.get();

		// Retrieve the list of transactions associated with the account
		List<Transaction> transactions = paymentAccount.getTransactions();

		// Initialize the balance to 0
		BigDecimal balance = BigDecimal.ZERO;

		// Loop through all transactions and calculate the balance based on transaction
		// type
		for (Transaction trans : transactions) {
			switch (trans.getTransactionType()) {
			case "opening_balance":
				balance = balance.add(trans.getAmount());
				break;

			case "deposit":
				balance = balance.add(trans.getAmount());
				break;

			case "sale":
				balance = balance.add(trans.getAmount());
				break;

			case "purchase":
				balance = balance.subtract(trans.getAmount());
				break;
			case "refund":
				balance = balance.subtract(trans.getAmount());
				break;
			case "credit note":
				balance = balance.subtract(trans.getAmount());
				break;
			}
		}

		return balance;
	}

	@Transactional
	public Transaction updateTransaction(Long transactionId, BigDecimal amount, String paymentMethod,
			String transactionType, String addedBy, String note, LocalDateTime date, String vendor) {
		// Fetch the existing transaction
		Transaction existingTransaction = transactionRepo.findById(transactionId)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));

		// Fetch the associated PaymentAccount
		PaymentAccount paymentAccount = existingTransaction.getPaymentAccount();
		if (paymentAccount == null) {
			throw new RuntimeException("Payment Account not found for this transaction");
		}

		// Get the previous balance before updating
		BigDecimal previousBalance = paymentAccount.getBalance() != null ? paymentAccount.getBalance()
				: BigDecimal.ZERO;

		// Reverse the effect of the old transaction amount
		switch (existingTransaction.getTransactionType().toLowerCase()) {
		case "deposit":
		case "sale":
		case "refund":
		case "credit note":
		case "opening_balance":
			previousBalance = previousBalance.subtract(existingTransaction.getAmount());
			break;
		case "purchase":
		case "payment":
		case "expense":
			previousBalance = previousBalance.add(existingTransaction.getAmount());
			break;
		default:
			throw new RuntimeException("Unknown transaction type");
		}

		// Update transaction details
		existingTransaction.setAmount(amount);
		existingTransaction.setPaymentMethod(paymentMethod);
		existingTransaction.setTransactionType(transactionType);
		existingTransaction.setAddedBy(addedBy);
		existingTransaction.setNote(note);
		existingTransaction.setVendor(vendor);
		existingTransaction.setDate(date);

		// Apply the new transaction amount
		BigDecimal updatedBalance = previousBalance;
		switch (transactionType.toLowerCase()) {
		case "deposit":
		case "sale":
		case "opening_balance":
			updatedBalance = previousBalance.add(amount);
			break;
		case "purchase":
		case "expense":
		case "refund":
		case "credit note":
			updatedBalance = previousBalance.subtract(amount);
			break;
		default:
			throw new RuntimeException("Unknown transaction type");
		}

		// Set the new balance in the transaction
		existingTransaction.setBalance(updatedBalance);

		// Save the updated transaction
		Transaction updatedTransaction = transactionRepo.save(existingTransaction);

		// Update the PaymentAccount balance
		paymentAccount.setBalance(updatedBalance);
		paymentAccountRepo.save(paymentAccount);

		return updatedTransaction;
	}

	@Override
	@Transactional
	public void updateAccountStatus(Long id, Long status) {
		Optional<PaymentAccount> paymentAccountOpt = paymentAccountRepo.findById(id);
		if (!paymentAccountOpt.isPresent()) {
			throw new RuntimeException("Payment Account not found");
		}

		PaymentAccount paymentAccount = paymentAccountOpt.get();
		paymentAccount.setStatus(status);

		paymentAccountRepo.save(paymentAccount);
	}

	// Get all purchases for a specific vendor

	public Map<String, List<Object>> getPaymentByVendor(String vendorName) {
		Map<String, List<Object>> vendorWiseOrders = new HashMap<>();

		// Fetch PO Orders for a specific vendor
		List<Transaction> poOrders = transactionRepo.findByVendorName(vendorName);
		vendorWiseOrders.put(vendorName, new ArrayList<>(poOrders));

		return vendorWiseOrders;
	}

	public Map<String, List<Object>> getPaymentByFranchiseName(String franchiseName) {
		Map<String, List<Object>> franchiseWiseOrders = new HashMap<>();

		// Fetch PO Orders for a specific vendor
		List<Transaction> transactions = transactionRepo.findByfranchiseName(franchiseName);
		franchiseWiseOrders.put(franchiseName, new ArrayList<>(transactions));

		return franchiseWiseOrders;
	}

}
