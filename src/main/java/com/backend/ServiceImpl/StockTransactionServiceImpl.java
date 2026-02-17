package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.StockTransaction;
import com.backend.Repository.StockTransactionRepo;
import com.backend.Service.StockTransactionService;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

	@Autowired
	private StockTransactionRepo stockTransactionRepo;

	@Override
	@Transactional
	public List<StockTransaction> saveStockTransactions(List<StockTransaction> stockTransactions) {
		validateStockTransactions(stockTransactions);
		return stockTransactionRepo.saveAll(stockTransactions);
	}

	@Override
	public void validateStockTransactions(List<StockTransaction> stockTransactions) {
		if (stockTransactions != null) {
			for (StockTransaction transaction : stockTransactions) {
				// Only validate for OUT transactions
				if (isOutTransaction(transaction.getTransactionType())) {

					int currentStock = getCurrentStock(
							transaction.getProductId(),
							transaction.getVariationId());

					if (currentStock < transaction.getQuantity()) {
						throw new RuntimeException(
								"Insufficient Stock for Product ID: "
										+ transaction.getProductId()
										+ " | Available: " + currentStock);
					}
				}
			}
		}
	}

	private boolean isOutTransaction(String type) {
		if (type == null)
			return false;
		return type.equals("di_sale") ||
				type.equals("so_sale") ||
				type.equals("transfer_out") ||
				type.equals("purchase_return") ||
				type.equals("product_replaced") ||
				type.equals("adjustment");
	}

	@Override
	public List<StockTransaction> getTransactionsByProduct(Long productId) {
		return stockTransactionRepo.findByProductId(productId);
	}

	@Override
	public List<StockTransaction> getTransactionsByVariation(Long variationId) {
		return stockTransactionRepo.findByVariationId(variationId);
	}

	@Override
	public List<StockTransaction> getAllTransactions() {
		return stockTransactionRepo.findAll();
	}

	@Override
	public List<StockTransaction> getTransactionsByProductAndVariation(Long productId, Long variationId) {
		return stockTransactionRepo.findByProductIdAndVariationId(productId, variationId);
	}

	@Override
	public int getCurrentStock(Long productId, Long variationId) {
		List<StockTransaction> transactions = stockTransactionRepo.findByProductIdAndVariationId(productId,
				variationId);
		int currentStock = 0;
		for (StockTransaction transaction : transactions) {
			
			// Add or subtract quantities based on the transaction type
			switch (transaction.getTransactionType()) {
				case "po_purchase":
				case "di_purchase":
				case "open_stock":
				case "transfer_in":
				case "sale_return":
				case "product_claimed":
					currentStock += transaction.getQuantity();
					break;
				case "di_sale":
				case "so_sale":
				case "transfer_out":
				case "purchase_return":
				case "product_replaced":
				case "adjustment":
					currentStock -= transaction.getQuantity();
					break;
			}
		}
		return currentStock;
	}

	@Override
	public int getCurrentStockByProduct(Long productId) {

		List<StockTransaction> transactions = stockTransactionRepo.findByProductId(productId);
		int currentStock = 0;
		for (StockTransaction transaction : transactions) {
			// Add or subtract quantities based on the transaction type
			switch (transaction.getTransactionType()) {
				case "po_purchase":
				case "di_purchase":
				case "open_stock":
				case "transfer_in":
				case "sale_return":
				case "product_claimed":
					currentStock += transaction.getQuantity();
					break;
				case "di_sale":
				case "transfer_out":
				case "so_sale":
				case "purchase_return":
				case "product_replaced":
				case "adjustment":
					currentStock -= transaction.getQuantity();
					break;
			}
		}
		return currentStock;

	}

	@Override
	public int getCurrentStockByvariation(Long variationId) {

		List<StockTransaction> transactions = stockTransactionRepo.findByVariationId(variationId);
		int currentStock = 0;
		for (StockTransaction transaction : transactions) {
			// Add or subtract quantities based on the transaction type
			switch (transaction.getTransactionType()) {
				case "po_purchase":
				case "di_purchase":
				case "open_stock":
				case "transfer_in":
				case "sale_return":
				case "product_claimed":
					currentStock += transaction.getQuantity();
					break;
				case "di_sale":
				case "so_sale":
				case "transfer_out":
				case "purchase_return":
				case "product_replaced":
				case "adjustment":
					currentStock -= transaction.getQuantity();
					break;
			}
		}
		return currentStock;
	}
}
