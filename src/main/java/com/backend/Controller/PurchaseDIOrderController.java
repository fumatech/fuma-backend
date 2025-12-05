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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.PurchaseDIOrder;
import com.backend.Service.PurchaseDIOrderService;
import com.backend.Util.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/purchase-di-order")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PurchaseDIOrderController {

	@Autowired
	private PurchaseDIOrderService purchaseDIOrderService;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@PostMapping("/save")
	public ResponseEntity<PurchaseDIOrder> createPurchase(@RequestParam("purchaseDIOrder") String purchaseOrderJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseDIOrder purchaseOrder = objectMapper.readValue(purchaseOrderJson, PurchaseDIOrder.class);

			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				purchaseOrder.setFile(filePath);
			}

			PurchaseDIOrder savedOrder = purchaseDIOrderService.savePurchaseDIOrder(purchaseOrder);
			return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<PurchaseDIOrder> updatePurchaseOrder(@PathVariable Long id,
			@RequestParam("purchaseDIOrder") String purchaseOrderJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		try {
			PurchaseDIOrder existingOrder = purchaseDIOrderService.getPurchaseDIOrderById(id).orElse(null);
			if (existingOrder == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseDIOrder updatedOrder = objectMapper.readValue(purchaseOrderJson, PurchaseDIOrder.class);
			updatedOrder.setId(id); // Important

			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				updatedOrder.setFile(filePath);
			} else {
				updatedOrder.setFile(existingOrder.getFile());
			}

			PurchaseDIOrder saved = purchaseDIOrderService.updatePurchaseDIOrder(id, updatedOrder);
			return new ResponseEntity<>(saved, HttpStatus.OK);

		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<PurchaseDIOrder>> getAllPurchases() {
		List<PurchaseDIOrder> orders = purchaseDIOrderService.getAllPurchaseDIOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<PurchaseDIOrder>> getPurchaseOrderById(@PathVariable Long id) {
		Optional<PurchaseDIOrder> orders = purchaseDIOrderService.getPurchaseDIOrderById(id);
		return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		purchaseDIOrderService.deletePurchaseDIOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllOrderIds() {
		List<String> orderIds = purchaseDIOrderService.getAllOrderIds();
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

}
