package com.backend.Controller;

import java.util.List;

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

import com.backend.DTO.PayComponentBulkRequest;
import com.backend.Entity.PayComponent;
import com.backend.Service.PayComponentService;

@RestController
@RequestMapping("/pay-component")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PayComponentController {

	@Autowired
	private PayComponentService payComponentService;

	// API to get all pay components
	@GetMapping("/all")
	public ResponseEntity<List<PayComponent>> getAllPayComponents() {
		List<PayComponent> payComponents = payComponentService.getAllPayComponents();
		return new ResponseEntity<>(payComponents, HttpStatus.OK);
	}

	// API to get a pay component by ID
	@GetMapping("/{id}")
	public ResponseEntity<PayComponent> getPayComponentById(@PathVariable Long id) {
		PayComponent payComponent = payComponentService.getPayComponentById(id);
		if (payComponent != null) {
			return new ResponseEntity<>(payComponent, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// API to save a new pay component
	@PostMapping("/add")
	public ResponseEntity<PayComponent> savePayComponent(@RequestBody PayComponent payComponent) {
		PayComponent savedPayComponent = payComponentService.savePayComponent(payComponent);
		return new ResponseEntity<>(savedPayComponent, HttpStatus.CREATED);
	}

	// API to update an existing pay component
	@PutMapping("/{id}")
	public ResponseEntity<PayComponent> updatePayComponent(@PathVariable Long id,
			@RequestBody PayComponent updatedPayComponent) {

		PayComponent updated = payComponentService.updatePayComponent(id, updatedPayComponent);

		if (updated != null) {
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// API to delete a pay component by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePayComponent(@PathVariable Long id) {
		payComponentService.deletePayComponentById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/bulk")
	public ResponseEntity<String> addBulkPayComponent(@RequestBody PayComponentBulkRequest request) {

		payComponentService.saveBulk(request);
		return ResponseEntity.ok("Pay components added successfully");
	}

}
