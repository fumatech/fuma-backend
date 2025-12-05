
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

import com.backend.Entity.SaleDIOrder;
import com.backend.Service.SaleDIOrderService;

@RestController
@RequestMapping("/sale-di-order")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class SaleDIOrderController {

	@Autowired
	private SaleDIOrderService saleDIOrderService;

	@PostMapping("/save")
	public ResponseEntity<SaleDIOrder> createSale(@RequestBody SaleDIOrder saleDIOrder) {
		SaleDIOrder savedOrderSale = saleDIOrderService.saveSaleDIOrder(saleDIOrder);
		return new ResponseEntity<>(savedOrderSale, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<SaleDIOrder>> getAllSales() {
		List<SaleDIOrder> orders = saleDIOrderService.getAllSaleDIOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Optional<SaleDIOrder>> getSaleOrderById(@PathVariable Long id) {
		Optional<SaleDIOrder> orders = saleDIOrderService.getSaleDIOrderById(id);
		return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<SaleDIOrder> updateSaleSoOrder(@PathVariable Long id, @RequestBody SaleDIOrder saleDIOrder) {

		SaleDIOrder updatedOrder = saleDIOrderService.updateSaleDIOrder(id, saleDIOrder);

		if (updatedOrder != null) {
			return ResponseEntity.ok(updatedOrder); // Return updated PO Order
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle not found case
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
		saleDIOrderService.deleteSaleDIOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllOrderIds() {
		List<String> orderIds = saleDIOrderService.getAllOrderIds();
		return new ResponseEntity<>(orderIds, HttpStatus.OK);
	}

	@GetMapping("/next-reference-number")
	public ResponseEntity<String> getNextReferenceNumber() {
		String nextRef = saleDIOrderService.getNextReferenceNumber();
		return new ResponseEntity<>(nextRef, HttpStatus.OK);
	}
}
