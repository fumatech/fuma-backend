package com.backend.Controller;

import java.util.List;

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

import com.backend.Entity.BusinessLocation;
import com.backend.Service.BusinessLocationService;

@RestController
@RequestMapping("/business-locations")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class BusinessLocationController {

	private final BusinessLocationService service;

	public BusinessLocationController(BusinessLocationService service) {
		this.service = service;
	}

	// ✅ SAVE
	@PostMapping("/save")
	public ResponseEntity<BusinessLocation> save(@RequestBody BusinessLocation businessLocation) {
		return ResponseEntity.ok(service.save(businessLocation));
	}

	// ✅ GET BY ID
	@GetMapping("/get/{id}")
	public ResponseEntity<BusinessLocation> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	// ✅ GET ALL
	@GetMapping("/getall")
	public ResponseEntity<List<BusinessLocation>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	// ✅ UPDATE
	@PutMapping("/update/{id}")
	public ResponseEntity<BusinessLocation> update(@PathVariable Long id,
			@RequestBody BusinessLocation businessLocation) {
		return ResponseEntity.ok(service.update(id, businessLocation));
	}

	// ✅ DELETE
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Deleted successfully");
	}
}
