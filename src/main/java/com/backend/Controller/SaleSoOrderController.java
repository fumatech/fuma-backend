
package com.backend.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.SaleSoOrder;
import com.backend.Service.SaleSoOrderService;

@RestController
@RequestMapping("/sale-so-order")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class SaleSoOrderController {

	@Autowired
	private SaleSoOrderService saleSoOrderService;

	@PostMapping("/save")
	public ResponseEntity<?> createSale(@RequestBody SaleSoOrder saleSoOrder) {
		try {
			SaleSoOrder savedOrderSale = saleSoOrderService.saveSaleSooOrder(saleSoOrder);
			return new ResponseEntity<>(savedOrderSale, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(java.util.Map.of("message", e.getMessage()), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<SaleSoOrder>> getAllSales() {
		List<SaleSoOrder> orders = saleSoOrderService.getAllSaleSoOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<SaleSoOrder>> getSaleOrderById(@PathVariable Long id) {
		Optional<SaleSoOrder> orders = saleSoOrderService.getSaleSoOrderById(id);
		return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<SaleSoOrder> updateSaleSoOrder(@PathVariable Long id, @RequestBody SaleSoOrder saleSoOrder) {

		SaleSoOrder updatedOrder = saleSoOrderService.updateSaleSoOrder(id, saleSoOrder);

		if (updatedOrder != null) {
			return ResponseEntity.ok(updatedOrder); // Return updated PO Order
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle not found case
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		saleSoOrderService.deleteSaleSoOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllOrderIds() {
		List<String> orderIds = saleSoOrderService.getAllOrderIds();
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@GetMapping("/next-reference-number")
	public ResponseEntity<String> getNextReferenceNumber() {
		String nextRef = saleSoOrderService.getNextReferenceNumber();
		return new ResponseEntity<>(nextRef, HttpStatus.OK);
	}

}
