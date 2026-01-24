package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.FranchisePurchaseReturn;
import com.backend.Entity.FranchisePurchaseReturnItems;
import com.backend.Entity.StockTransaction;
import com.backend.Entity.Transaction;
import com.backend.Repository.FranchisePurchaseReturnRepo;
import com.backend.Repository.PaymentAccountRepo;
import com.backend.Repository.TransactionRepo;
import com.backend.Service.FranchisePurchaseReturnService;
import com.backend.Service.IdGenerator;

@Service
public class FranchisePurchaseReturnServiceImpl implements FranchisePurchaseReturnService {

	@Autowired
	private FranchisePurchaseReturnRepo repo;

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private FranchisePurchaseReturnRepo returnRepo;

	@Autowired
	private PaymentAccountRepo paymentAccountRepo;

	@Override
	public Transaction saveTransaction(Transaction transaction) {

		// 🔹 Link Franchise Purchase Return
		if (transaction.getFranchisePurchaseReturnId() != null) {
			FranchisePurchaseReturn pr = returnRepo.findById(transaction.getFranchisePurchaseReturnId())
					.orElseThrow(() -> new RuntimeException("Return not found"));

			transaction.setFranchisePurchaseReturn(pr);
		}

		// 🔹 Link Payment Account
		if (transaction.getPaymentAccountId() != null) {
			transaction.setPaymentAccount(paymentAccountRepo.findById(transaction.getPaymentAccountId())
					.orElseThrow(() -> new RuntimeException("Payment account not found")));
		}

		transaction.setDate(LocalDateTime.now());

		return transactionRepo.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionsByReturnId(Long returnId) {
		return transactionRepo.findByFranchisePurchaseReturn_Id(returnId);
	}

	@Override
	public BigDecimal getTotalPaidAmount(Long returnId) {
		return transactionRepo.totalPaidAmount(returnId);
	}

	@Override
	public FranchisePurchaseReturn saveFranchisePurchaseReturn(FranchisePurchaseReturn returnOrder) {
		// ✅ Generate IDs here, not in entity
		if (returnOrder.getFranchisePurchaseReturnId() == null) {
			returnOrder.setFranchisePurchaseReturnId(idGenerator.generateFranchisePurchaseReturnId());
		}
		if (returnOrder.getInvoiceNumber() == null) {
			returnOrder.setInvoiceNumber(idGenerator.generateFranchisePurchaseReturnInvoiceNumber());
		}

		// ✅ Link child items to parent
		if (returnOrder.getFranchisePurchaseReturnItems() != null) {
			for (FranchisePurchaseReturnItems item : returnOrder.getFranchisePurchaseReturnItems()) {
				item.setFranchisePurchaseReturn(returnOrder);
			}
		}

		// ✅ Link stock transactions to parent
		if (returnOrder.getStockTransactions() != null) {
			for (StockTransaction stock : returnOrder.getStockTransactions()) {
				stock.setFranchisePurchaseReturn(returnOrder);
			}
		}

		return repo.save(returnOrder);
	}

	@Override
	public List<FranchisePurchaseReturn> getAllFranchisePurchaseReturns() {
		return repo.findAll();
	}

	@Override
	public Optional<FranchisePurchaseReturn> getFranchisePurchaseReturnById(Long id) {
		return repo.findById(id);
	}

	@Override
	public FranchisePurchaseReturn updateFranchisePurchaseReturn(Long id, FranchisePurchaseReturn updatedReturn) {
		Optional<FranchisePurchaseReturn> existingOpt = repo.findById(id);

		if (existingOpt.isPresent()) {
			FranchisePurchaseReturn existing = existingOpt.get();

			// Update simple fields
			existing.setVendor(updatedReturn.getVendor());
			existing.setFranchisePurchaseReturnId(updatedReturn.getFranchisePurchaseReturnId());
			existing.setStatus(updatedReturn.getStatus());
			existing.setPaymentStatus(updatedReturn.getPaymentStatus());
			existing.setAddedBy(updatedReturn.getAddedBy());
			existing.setReferenceNumber(updatedReturn.getReferenceNumber());
			existing.setOrderDate(updatedReturn.getOrderDate());
			existing.setLocation(updatedReturn.getLocation());
			existing.setFile(updatedReturn.getFile());
			existing.setCustomer(updatedReturn.getCustomer());
			existing.setFranchiseId(updatedReturn.getFranchiseId());
			existing.setInvoiceNumber(updatedReturn.getInvoiceNumber());
			existing.setCustomerId(updatedReturn.getCustomerId());
			existing.setTotalItems(updatedReturn.getTotalItems());
			existing.setCustomerId(updatedReturn.getCustomerId());
			existing.setNetTotalAmount(updatedReturn.getNetTotalAmount());
			existing.setTotalShippedItems(updatedReturn.getTotalShippedItems());
			existing.setAdditionalNotes(updatedReturn.getAdditionalNotes());

			// Replace Return Items
			existing.getFranchisePurchaseReturnItems().clear();
			if (updatedReturn.getFranchisePurchaseReturnItems() != null) {
				for (FranchisePurchaseReturnItems item : updatedReturn.getFranchisePurchaseReturnItems()) {
					item.setFranchisePurchaseReturn(existing);
					existing.getFranchisePurchaseReturnItems().add(item);
				}
			}

			// Replace Stock Transactions
			existing.getStockTransactions().clear();
			if (updatedReturn.getStockTransactions() != null) {
				for (StockTransaction stock : updatedReturn.getStockTransactions()) {
					stock.setFranchisePurchaseReturn(existing);
					existing.getStockTransactions().add(stock);
				}
			}

			return repo.save(existing);
		}

		return null;
	}

	@Override
	public void deleteFranchisePurchaseReturn(Long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllReturnIds() {
		return repo.findAll().stream().map(FranchisePurchaseReturn::getFranchisePurchaseReturnId)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<FranchisePurchaseReturn> getFranchisePurchaseReturnByIdString(String id) {
		return Optional.ofNullable(repo.findByFranchisePurchaseReturnId(id));
	}

	@Override
	public List<String> getReturnIdsByStatus(Long status) {
		return repo.findReturnIdsByStatus(status);
	}

	@Override
	public List<FranchisePurchaseReturn> getPendingReturns() {
		return repo.findByReturnStatus(0L);
	}

	@Override
	public List<FranchisePurchaseReturn> getAcceptedReturns() {
		return repo.findByReturnStatus(1L);
	}

	@Override
	public List<FranchisePurchaseReturn> getRejectedReturns() {
		return repo.findByReturnStatus(2L);
	}

	@Override
	public List<FranchisePurchaseReturn> getShipReturns() {
		return repo.findByReturnStatus(3L);
	}

	@Override
	public Long getTotalShippedItems(String purchaseReturnId) {
		return repo.getTotalShippedItems(purchaseReturnId);
	}

	@Override
	public List<FranchisePurchaseReturn> getReturnsByFranchiseId(String franchiseId) {
		return repo.findByFranchiseId(franchiseId);
	}

	@Override
	public List<FranchisePurchaseReturn> getReturnsByFranchiseIdAndStatus(String franchiseId, Long status) {
		return repo.findByFranchiseIdAndStatus(franchiseId, status);
	}

	@Override
	public BigDecimal getTotalReturnAmountByFranchise(String franchiseId) {
		return repo.totalReturnAmountByFranchise(franchiseId);
	}
}
