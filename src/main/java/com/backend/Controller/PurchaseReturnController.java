package com.backend.Controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.PurchaseOrder;
import com.backend.Entity.PurchaseReturn;
import com.backend.Service.PurchaseReturnService;
import com.backend.Util.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/purchase-return")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PurchaseReturnController {

	@Autowired
	private PurchaseReturnService purchaseReturnService;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	// Save PurchaseReturn with receipt file
	@PostMapping("/save")
	public ResponseEntity<PurchaseReturn> createPurchase(@RequestParam("purchaseReturn") String purchaseReturnJson,
			@RequestParam(value = "receipt", required = false) MultipartFile receiptFile) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseReturn purchaseReturn = objectMapper.readValue(purchaseReturnJson, PurchaseReturn.class);

			if (receiptFile != null && !receiptFile.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + receiptFile.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, receiptFile);
				purchaseReturn.setReceipt(filePath);
			}

			PurchaseReturn savedPurchaseReturn = purchaseReturnService.savePurchaseReturn(purchaseReturn);
			return new ResponseEntity<>(savedPurchaseReturn, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	// Update PurchaseReturn with receipt file
	@PutMapping("/update/{id}")
	public ResponseEntity<PurchaseReturn> updatePurchaseReturn(@PathVariable Long id,
			@RequestParam("purchaseReturn") String purchaseReturnJson,
			@RequestParam(value = "receipt", required = false) MultipartFile receiptFile) {
		try {
			Optional<PurchaseReturn> existingOpt = purchaseReturnService.getPurchaseReturnById(id);
			if (!existingOpt.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			PurchaseReturn existingOrder = existingOpt.get();

			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseReturn updatedOrder = objectMapper.readValue(purchaseReturnJson, PurchaseReturn.class);
			updatedOrder.setId(id); // keep same ID

			if (receiptFile != null && !receiptFile.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + receiptFile.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, receiptFile);
				updatedOrder.setReceipt(filePath);
			} else {
				updatedOrder.setReceipt(existingOrder.getReceipt()); // keep old receipt
			}

			PurchaseReturn saved = purchaseReturnService.updatePurchaseReturn(id, updatedOrder);
			return new ResponseEntity<>(saved, HttpStatus.OK);

		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<PurchaseReturn>> getAllPurchaseReturns() {
		List<PurchaseReturn> returns = purchaseReturnService.getAllPurchaseReturns();
		return new ResponseEntity<>(returns, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<PurchaseReturn>> getPurchaseOrderById(@PathVariable Long id) {
		Optional<PurchaseReturn> returns = purchaseReturnService.getPurchaseReturnById(id);
		return returns.isPresent() ? new ResponseEntity<>(returns, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		purchaseReturnService.deletePurchaseReturn(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllReturnIds() {
		List<String> returnIds = purchaseReturnService.getAllReturnIds();
		return new ResponseEntity<>(returnIds, HttpStatus.OK);
	}

	// Get a Purchase Order by ID
	@GetMapping("/getPoDataById/{id}")
	public ResponseEntity<PurchaseReturn> getPurchaseOrderByPoId(@PathVariable String id) {
		Optional<PurchaseReturn> order = purchaseReturnService.getPurchaseReturnByPRId(id);
		return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/getOrderIdsByStatus/{status}")
	public ResponseEntity<List<String>> getOrderIdsByStatus(@PathVariable Long status) {
		List<String> orderIds = purchaseReturnService.getPurchaseReturnIdsByStatus(status);
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<PurchaseReturn> updatePurchaseStatus(@PathVariable Long id,
			@RequestBody PurchaseOrder updatedStatus) {
		Optional<PurchaseReturn> existingOrder = purchaseReturnService.getPurchaseReturnById(id);

		if (existingOrder.isPresent()) {
			PurchaseReturn purchaseReturn = existingOrder.get();
			purchaseReturn.setStatus(updatedStatus.getStatus());
			PurchaseReturn updatedOrder = purchaseReturnService.savePurchaseReturn(purchaseReturn);
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Endpoint for fetching pending orders
	@GetMapping("/getPendingOrders")
	public ResponseEntity<List<PurchaseReturn>> getPendingOrders() {
		List<PurchaseReturn> pendingOrders = purchaseReturnService.getPendingOrders();
		return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
	}

	// Endpoint for fetching accepted orders
	@GetMapping("/getAcceptedOrders")
	public ResponseEntity<List<PurchaseReturn>> getAcceptedOrders() {
		List<PurchaseReturn> acceptedOrders = purchaseReturnService.getAcceptedOrders();
		return new ResponseEntity<>(acceptedOrders, HttpStatus.OK);
	}

	// Endpoint for fetching rejected orders
	@GetMapping("/getRejectedOrders")
	public ResponseEntity<List<PurchaseReturn>> getRejectedOrders() {
		List<PurchaseReturn> rejectedOrders = purchaseReturnService.getRejectedOrders();
		return new ResponseEntity<>(rejectedOrders, HttpStatus.OK);
	}

	// Endpoint for fetching ship orders
	@GetMapping("/getShipOrders")
	public ResponseEntity<List<PurchaseReturn>> getShipOrders() {
		List<PurchaseReturn> shipOrders = purchaseReturnService.getShipOrders();
		return new ResponseEntity<>(shipOrders, HttpStatus.OK);
	}

	// Endpoint for fetching ship orders
	@GetMapping("/getFinalOrders")
	public ResponseEntity<List<PurchaseReturn>> getFinalOrders() {
		List<PurchaseReturn> finalOrders = purchaseReturnService.getReturnedOrders();
		return new ResponseEntity<>(finalOrders, HttpStatus.OK);
	}

	@GetMapping("/getTotalShippedItems/{purchaseOrderId}")
	public ResponseEntity<Long> getTotalShippedItems(@PathVariable String purchaseReturnId) {
		Long totalShippedItems = purchaseReturnService.getTotalShippedItems(purchaseReturnId);
		return ResponseEntity.ok(totalShippedItems);
	}

	@PutMapping("/updatePaymentStatus/{id}")
	public ResponseEntity<PurchaseReturn> updatePaymentStatus(@PathVariable Long id,
			@RequestBody PurchaseReturn updatedData) {

		// Try to find the existing PurchaseReturn by ID
		Optional<PurchaseReturn> optionalPurchaseReturn = purchaseReturnService.getPurchaseReturnById(id);

		if (optionalPurchaseReturn.isPresent()) {
			PurchaseReturn existingReturn = optionalPurchaseReturn.get();

			// Update only the paymentStatus field
			existingReturn.setPaymentStatus(updatedData.getPaymentStatus());

			// Save the updated entity
			PurchaseReturn savedReturn = purchaseReturnService.savePurchaseReturn(existingReturn);

			return ResponseEntity.ok(savedReturn);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
