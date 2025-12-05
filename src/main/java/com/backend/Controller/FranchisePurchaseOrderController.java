package com.backend.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.FranchisePurchaseOrder;
import com.backend.Service.FranchisePurchaseOrderService;

@RestController
@RequestMapping("/franchisepurchaseorder")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class FranchisePurchaseOrderController {

	@Autowired
	private FranchisePurchaseOrderService franchisePurchaseOrderService;

	@PostMapping("/save")
	public ResponseEntity<FranchisePurchaseOrder> createPurchase(@RequestBody FranchisePurchaseOrder purchaseOrder) {
		FranchisePurchaseOrder savedOrder = franchisePurchaseOrderService.savePurchaseOrder(purchaseOrder);
		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<FranchisePurchaseOrder>> getAllPurchases() {
		List<FranchisePurchaseOrder> orders = franchisePurchaseOrderService.getAllPurchaseOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<FranchisePurchaseOrder>> getPurchaseOrderById(@PathVariable Long id) {
		Optional<FranchisePurchaseOrder> order = franchisePurchaseOrderService.getPurchaseOrderById(id);
		return order.isPresent() ? new ResponseEntity<>(order, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<FranchisePurchaseOrder> updatePurchaseOrder(@PathVariable Long id,
			@RequestBody FranchisePurchaseOrder purchaseOrder) {
		FranchisePurchaseOrder updatedOrder = franchisePurchaseOrderService.updatePurchaseOrder(id, purchaseOrder);
		return updatedOrder != null ? new ResponseEntity<>(updatedOrder, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		franchisePurchaseOrderService.deletePurchaseOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllOrderIds() {
		List<String> orderIds = franchisePurchaseOrderService.getAllOrderIds();
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@GetMapping("/getPoDataById/{id}")
	public ResponseEntity<FranchisePurchaseOrder> getPurchaseOrderByPoId(@PathVariable String id) {
		Optional<FranchisePurchaseOrder> order = franchisePurchaseOrderService.getPurchaseOrderByPoId(id);
		return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/getOrderIdsByStatus/{status}")
	public ResponseEntity<List<String>> getOrderIdsByStatus(@PathVariable Long status) {
		List<String> orderIds = franchisePurchaseOrderService.getPurchaseOrderIdsByStatus(status);
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<FranchisePurchaseOrder> updatePurchaseStatus(@PathVariable Long id,
			@RequestBody FranchisePurchaseOrder updatedStatus) {
		Optional<FranchisePurchaseOrder> existingOrder = franchisePurchaseOrderService.getPurchaseOrderById(id);

		if (existingOrder.isPresent()) {
			FranchisePurchaseOrder order = existingOrder.get();
			order.setStatus(updatedStatus.getStatus());
			FranchisePurchaseOrder updatedOrder = franchisePurchaseOrderService.savePurchaseOrder(order);
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getPendingOrders")
	public ResponseEntity<List<FranchisePurchaseOrder>> getPendingOrders() {
		List<FranchisePurchaseOrder> orders = franchisePurchaseOrderService.getPendingOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getAcceptedOrders")
	public ResponseEntity<List<FranchisePurchaseOrder>> getAcceptedOrders() {
		List<FranchisePurchaseOrder> orders = franchisePurchaseOrderService.getAcceptedOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getRejectedOrders")
	public ResponseEntity<List<FranchisePurchaseOrder>> getRejectedOrders() {
		List<FranchisePurchaseOrder> orders = franchisePurchaseOrderService.getRejectedOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getShipOrders")
	public ResponseEntity<List<FranchisePurchaseOrder>> getShipOrders() {
		List<FranchisePurchaseOrder> orders = franchisePurchaseOrderService.getShipOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getTotalShippedItems/{purchaseOrderId}")
	public ResponseEntity<Long> getTotalShippedItems(@PathVariable String purchaseOrderId) {
		Long totalShippedItems = franchisePurchaseOrderService.getTotalShippedItems(purchaseOrderId);
		return ResponseEntity.ok(totalShippedItems);
	}

	@PutMapping("/updateDispatchStatus/{id}")
	public ResponseEntity<FranchisePurchaseOrder> updateDispatchStatus(@PathVariable Long id,
			@RequestBody Map<String, String> request) {

		String dispatchStatus = request.get("dispatchStatus");

		if (dispatchStatus == null || dispatchStatus.trim().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		FranchisePurchaseOrder updatedOrder = franchisePurchaseOrderService.updateDispatchStatus(id, dispatchStatus);
		if (updatedOrder != null) {
			return ResponseEntity.ok(updatedOrder);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/updateStatusByOrderId/{franchisePurchaseOrderId}")
	public ResponseEntity<FranchisePurchaseOrder> updateStatusByOrderId(@PathVariable String franchisePurchaseOrderId,
			@RequestBody Map<String, Long> requestBody) {

		Long newStatus = requestBody.get("status");

		if (newStatus == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<FranchisePurchaseOrder> optionalOrder = franchisePurchaseOrderService
				.getPurchaseOrderByPoId(franchisePurchaseOrderId);

		if (optionalOrder.isPresent()) {
			FranchisePurchaseOrder order = optionalOrder.get();
			order.setStatus(newStatus);
			FranchisePurchaseOrder updatedOrder = franchisePurchaseOrderService.savePurchaseOrder(order);
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
