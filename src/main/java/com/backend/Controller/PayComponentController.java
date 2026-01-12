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

import com.backend.Entity.PayComponent;
import com.backend.Service.PayComponentService;

@RestController
@RequestMapping("/pay-component")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PayComponentController {

	@Autowired
	private PayComponentService payComponentService;

	@GetMapping("/all")
	public ResponseEntity<List<PayComponent>> getAllPayComponents() {
		return ResponseEntity.ok(payComponentService.getAllPayComponents());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PayComponent> getById(@PathVariable Long id) {
		PayComponent pc = payComponentService.getPayComponentById(id);
		return pc != null ? ResponseEntity.ok(pc) : ResponseEntity.notFound().build();
	}

	@PostMapping("/add")
	public ResponseEntity<PayComponent> save(@RequestBody PayComponent payComponent) {
		return ResponseEntity.status(HttpStatus.CREATED).body(payComponentService.savePayComponent(payComponent));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PayComponent> update(@PathVariable Long id, @RequestBody PayComponent payComponent) {

		PayComponent updated = payComponentService.updatePayComponent(id, payComponent);
		return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		payComponentService.deletePayComponentById(id);
		return ResponseEntity.noContent().build();
	}
}
