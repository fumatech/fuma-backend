package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.StockTransaction;
import com.backend.Repository.StockTransactionRepo;
import com.backend.Service.StockTransactionService;

@RestController
@RequestMapping("/stock-transactions")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class StockTransactionController {

	@Autowired
	private StockTransactionService stockTransactionService;

	@Autowired
	private StockTransactionRepo stockTransactionRepo;

	// Endpoint to create multiple stock transactions
	@PostMapping("/add")
	public ResponseEntity<List<StockTransaction>> addStockTransactions(
			@RequestBody List<StockTransaction> stockTransactions) {
		try {
			// Save all stock transactions
			List<StockTransaction> savedTransactions = stockTransactionService.saveStockTransactions(stockTransactions);
			return new ResponseEntity<>(savedTransactions, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	// Add an update endpoint
	@PutMapping("/update/{id}")
	public ResponseEntity<StockTransaction> updateStockTransaction(@PathVariable Long id,
			@RequestBody StockTransaction stockTransaction) {
		return stockTransactionRepo.findById(id).map(existing -> {
			existing.setQuantity(stockTransaction.getQuantity());
			existing.setDate(stockTransaction.getDate());
			existing.setNote(stockTransaction.getNote());
			existing.setPrice(stockTransaction.getPrice());
			existing.setTransactionType(stockTransaction.getTransactionType());
			return new ResponseEntity<>(stockTransactionRepo.save(existing), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Endpoint to get stock transactions by product ID
	@GetMapping("/by-product/{productId}")
	public ResponseEntity<List<StockTransaction>> getTransactionsByProduct(@PathVariable Long productId) {
		List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduct(productId);
		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	// Endpoint to get stock transactions by variation ID
	@GetMapping("/by-variation/{variationId}")
	public ResponseEntity<List<StockTransaction>> getTransactionsByVariation(@PathVariable Long variationId) {
		List<StockTransaction> transactions = stockTransactionService.getTransactionsByVariation(variationId);
		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	// Endpoint to get stock transactions by product ID and variation ID
	@GetMapping("/by-product-variation/{productId}/{variationId}")
	public ResponseEntity<List<StockTransaction>> getTransactionsByProductAndVariation(@PathVariable Long productId,
			@PathVariable Long variationId) {
		List<StockTransaction> transactions = stockTransactionService.getTransactionsByProductAndVariation(productId,
				variationId);
		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	// Endpoint to get current stock for a given productId and variationId
	@GetMapping("/current-stock/{productId}/{variationId}")
	public ResponseEntity<Integer> getCurrentStock(@PathVariable Long productId, @PathVariable Long variationId) {
		int currentStock = stockTransactionService.getCurrentStock(productId, variationId);
		return new ResponseEntity<>(currentStock, HttpStatus.OK);
	}

	// Endpoint to get current stock for a given productId and variationId
	@GetMapping("/current-stock/{productId}")
	public ResponseEntity<Integer> getCurrentStockByProduct(@PathVariable Long productId) {
		int currentStock = stockTransactionService.getCurrentStockByProduct(productId);
		return new ResponseEntity<>(currentStock, HttpStatus.OK);
	}

	// Endpoint to get current stock for a given productId and variationId
	@GetMapping("/current-stock-byvariation/{variationId}")
	public ResponseEntity<Integer> getCurrentStockByvariation(@PathVariable Long variationId) {
		int currentStock = stockTransactionService.getCurrentStockByvariation(variationId);
		return new ResponseEntity<>(currentStock, HttpStatus.OK);
	}

	// Endpoint to get all stock transactions (optional)
	@GetMapping("/getall")
	public ResponseEntity<List<StockTransaction>> getAllTransactions() {
		List<StockTransaction> transactions = stockTransactionService.getAllTransactions();
		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	// Bulk endpoint to get current stock for multiple products/variations at once
	@PostMapping("/current-stock/bulk")
	public ResponseEntity<java.util.Map<String, Integer>> getBulkCurrentStock(
			@RequestBody java.util.List<java.util.Map<String, Long>> requests) {
		java.util.Map<String, Integer> stocks = stockTransactionService.getBulkCurrentStock(requests);
		return new ResponseEntity<>(stocks, HttpStatus.OK);
	}
}
