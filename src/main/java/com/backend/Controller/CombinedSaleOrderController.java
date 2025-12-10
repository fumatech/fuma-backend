package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ServiceImpl.CombinedSaleOrderService;

@RestController
@RequestMapping("/combined-orders")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class CombinedSaleOrderController {

	@Autowired
	private CombinedSaleOrderService combinedSaleOrderService;

	@GetMapping("/getall")
	public ResponseEntity<List<Object>> getAllCombinedOrders() {
		List<Object> orders = combinedSaleOrderService.getAllCombinedOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/getbyfranchise/{franchise}")
	public ResponseEntity<Map<String, List<Object>>> getSaleByFranchise(@PathVariable String franchise) {
		Map<String, List<Object>> orders = combinedSaleOrderService.getSaleByFranchise(franchise);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/with-tax")
	public ResponseEntity<Map<String, List<Object>>> getAllOrdersWithTax() {
		return ResponseEntity.ok(combinedSaleOrderService.getAllSaleOrdersWithTax());
	}
}
