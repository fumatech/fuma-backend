package com.backend.Controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.PurchaseOrder;
import com.backend.Service.PurchaseOrderService;
import com.backend.Util.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/purchaseorder")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@PostMapping("/save")
	public ResponseEntity<PurchaseOrder> createPurchase(@RequestParam("purchaseOrder") String purchaseOrderJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseOrder purchaseOrder = objectMapper.readValue(purchaseOrderJson, PurchaseOrder.class);

			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				purchaseOrder.setFile(filePath);
			}

			PurchaseOrder savedOrderPurchase = purchaseOrderService.savePurchaseOrder(purchaseOrder);
			return new ResponseEntity<>(savedOrderPurchase, HttpStatus.CREATED);

		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long id,
			@RequestParam("purchaseOrder") String purchaseOrderJson,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			Optional<PurchaseOrder> existingOpt = purchaseOrderService.getPurchaseOrderById(id);
			if (!existingOpt.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			PurchaseOrder existingOrder = existingOpt.get();

			ObjectMapper objectMapper = new ObjectMapper();
			PurchaseOrder updatedOrder = objectMapper.readValue(purchaseOrderJson, PurchaseOrder.class);
			updatedOrder.setId(id);

			if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String filePath = fileUploadUtil.saveFile(fileName, file);
				updatedOrder.setFile(filePath);
			} else {
				updatedOrder.setFile(existingOrder.getFile());
			}

			PurchaseOrder saved = purchaseOrderService.updatePurchaseOrder(id, updatedOrder);
			return new ResponseEntity<>(saved, HttpStatus.OK);

		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<PurchaseOrder>> getAllPurchases() {
		List<PurchaseOrder> orders = purchaseOrderService.getAllPurchaseOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<PurchaseOrder>> getPurchaseOrderById(@PathVariable Long id) {
		Optional<PurchaseOrder> orders = purchaseOrderService.getPurchaseOrderById(id);
		return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		purchaseOrderService.deletePurchaseOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllOrderIds() {
		List<String> orderIds = purchaseOrderService.getAllOrderIds();
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@GetMapping("/getPoDataById/{id}")
	public ResponseEntity<PurchaseOrder> getPurchaseOrderByPoId(@PathVariable String id) {
		Optional<PurchaseOrder> order = purchaseOrderService.getPurchaseOrderByPoId(id);
		return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/getOrderIdsByStatus/{status}")
	public ResponseEntity<List<String>> getOrderIdsByStatus(@PathVariable Long status) {
		List<String> orderIds = purchaseOrderService.getPurchaseOrderIdsByStatus(status);
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<PurchaseOrder> updatePurchaseStatus(@PathVariable Long id,
			@RequestBody PurchaseOrder updatedStatus) {
		Optional<PurchaseOrder> existingOrder = purchaseOrderService.getPurchaseOrderById(id);

		if (existingOrder.isPresent()) {
			PurchaseOrder purchaseOrder = existingOrder.get();
			purchaseOrder.setStatus(updatedStatus.getStatus());
			PurchaseOrder updatedOrder = purchaseOrderService.savePurchaseOrder(purchaseOrder);
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getPendingOrders")
	public ResponseEntity<List<PurchaseOrder>> getPendingOrders() {
		List<PurchaseOrder> pendingOrders = purchaseOrderService.getPendingOrders();
		return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
	}

	@GetMapping("/getAcceptedOrders")
	public ResponseEntity<List<PurchaseOrder>> getAcceptedOrders() {
		List<PurchaseOrder> acceptedOrders = purchaseOrderService.getAcceptedOrders();
		return new ResponseEntity<>(acceptedOrders, HttpStatus.OK);
	}

	@GetMapping("/getRejectedOrders")
	public ResponseEntity<List<PurchaseOrder>> getRejectedOrders() {
		List<PurchaseOrder> rejectedOrders = purchaseOrderService.getRejectedOrders();
		return new ResponseEntity<>(rejectedOrders, HttpStatus.OK);
	}

	@GetMapping("/getShipOrders")
	public ResponseEntity<List<PurchaseOrder>> getShipOrders() {
		List<PurchaseOrder> shipOrders = purchaseOrderService.getShipOrders();
		return new ResponseEntity<>(shipOrders, HttpStatus.OK);
	}

	@GetMapping("/getFinalOrders")
	public ResponseEntity<List<PurchaseOrder>> getFinalOrders() {
		List<PurchaseOrder> finalOrders = purchaseOrderService.getFinalOrders();
		return new ResponseEntity<>(finalOrders, HttpStatus.OK);
	}

	@GetMapping("/getTotalShippedItems/{purchaseOrderId}")
	public ResponseEntity<Long> getTotalShippedItems(@PathVariable String purchaseOrderId) {
		Long totalShippedItems = purchaseOrderService.getTotalShippedItems(purchaseOrderId);
		return ResponseEntity.ok(totalShippedItems);
	}

	@PutMapping("/updateStatusByOrderId/{purchaseOrderId}")
	public ResponseEntity<PurchaseOrder> updateStatusByOrderId(@PathVariable String purchaseOrderId,
			@RequestBody Map<String, Long> requestBody) {

		Long newStatus = requestBody.get("status");

		if (newStatus == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<PurchaseOrder> optionalOrder = purchaseOrderService.getPurchaseOrderByPoId(purchaseOrderId);

		if (optionalOrder.isPresent()) {
			PurchaseOrder order = optionalOrder.get();
			order.setStatus(newStatus);
			PurchaseOrder updatedOrder = purchaseOrderService.savePurchaseOrder(order);
			return ResponseEntity.ok(updatedOrder);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
