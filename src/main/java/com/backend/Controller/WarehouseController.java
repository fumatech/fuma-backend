package com.backend.Controller;

import java.util.HashMap;
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

import com.backend.DTO.WarehouseLoginRequest;
import com.backend.Entity.Warehouse;
import com.backend.Service.WarehouseService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;

	@PostMapping("/save")
	public ResponseEntity<Warehouse> save(@RequestBody Warehouse warehouse) {
		return new ResponseEntity<>(warehouseService.save(warehouse), HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Warehouse>> getAll() {
		return ResponseEntity.ok(warehouseService.getAll());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Warehouse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(warehouseService.getById(id));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Warehouse> update(@PathVariable Long id, @RequestBody Warehouse warehouse) {
		return ResponseEntity.ok(warehouseService.update(id, warehouse));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		warehouseService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody WarehouseLoginRequest request, HttpSession session) {
		Optional<Warehouse> authWarehouse = warehouseService.authenticate(request.getUsername(), request.getPassword());

		if (authWarehouse.isEmpty()) {
			return new ResponseEntity<>("Invalid credentials or account inactive", HttpStatus.UNAUTHORIZED);
		}

		Warehouse warehouse = authWarehouse.get();
		session.setAttribute("warehouseId", warehouse.getId());
		session.setAttribute("warehouseUsername", warehouse.getUsername());

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Login successful");
		response.put("warehouse", warehouse);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	}
}

