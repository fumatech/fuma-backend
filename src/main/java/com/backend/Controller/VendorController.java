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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.LoginRequest;
import com.backend.Entity.Vendor;
import com.backend.Service.VendorService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vendor")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	// Endpoint to create a new vendor
	@PostMapping("/save")
	public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
		Vendor savedVendor = vendorService.saveVendor(vendor);
		return ResponseEntity.ok(savedVendor);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
		boolean isAuthenticated = vendorService.authenticate(request.getEmail(), request.getPassword());

		if (isAuthenticated) {
			// Create or get the session
			session.setAttribute("userEmail", request.getEmail());
			return new ResponseEntity<>("Login successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid credentials or account inactive", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate(); // Invalidate the session
		return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	}

	// Endpoint to get all vendors
	@GetMapping("/getall")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		List<Vendor> vendors = vendorService.getAllVendors();
		return ResponseEntity.ok(vendors);
	}

	// Endpoint to get a vendor by its ID
	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
		Vendor vendor = vendorService.getVendorById(id);
		if (vendor != null) {
			return ResponseEntity.ok(vendor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Endpoint to update a vendor
	@PutMapping("/update/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor updatedVendor) {
		Vendor vendor = vendorService.updateVendor(id, updatedVendor);
		if (vendor != null) {
			return ResponseEntity.ok(vendor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Endpoint to delete a vendor by its ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteVendorById(@PathVariable Long id) {
		vendorService.deleteVendorById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/firmname/email/{email}")
	public ResponseEntity<Map<String, String>> getFirmNameByEmail(@PathVariable String email) {
		Optional<String> firmName = vendorService.findFirmNameByEmail(email);
		if (firmName.isPresent()) {
			// Return a JSON object with a key "firmName" and the firm name value
			Map<String, String> response = new HashMap<>();
			response.put("firmName", firmName.get());
			return ResponseEntity.ok(response); // Return the response as a JSON object
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/check-email")
	public ResponseEntity<String> checkVendorEmail(@RequestParam String email) {
		Optional<Vendor> vendorExists = vendorService.findByEmail(email);

		if (vendorExists.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Email exists");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist");
		}
	}

	@GetMapping("/check-vendorid")
	public ResponseEntity<String> checkVendorId(@RequestParam String vendorId) {
		Optional<Vendor> vendorExists = vendorService.findByVendorId(vendorId);

		if (vendorExists.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Vendor ID exists");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor ID does not exist");
		}
	}

	@PutMapping("/toggle-active/{id}")
	public ResponseEntity<Map<String, Object>> toggleVendorActiveStatus(@PathVariable Long id,
			@RequestParam boolean isActive) {

		Vendor updatedVendor = vendorService.toggleActiveStatus(id, isActive);
		if (updatedVendor != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Vendor status updated");
			response.put("isActive", updatedVendor.getIsActive());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Vendor not found"));
		}
	}

	@GetMapping("/getallactive")
	public ResponseEntity<List<Vendor>> getAllActiveVendors() {
		List<Vendor> activeVendors = vendorService.getAllActiveVendors();
		return ResponseEntity.ok(activeVendors);
	}

	@GetMapping("/getallinactive")
	public ResponseEntity<List<Vendor>> getAllInactiveVendors() {
		List<Vendor> inactiveVendors = vendorService.getAllInactiveVendors();
		return ResponseEntity.ok(inactiveVendors);
	}

}
