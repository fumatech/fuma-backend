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

import com.backend.Entity.FranchiseWarrantyClaim;
import com.backend.Service.FranchiseWarrantyClaimService;

@RestController
@RequestMapping("/franchise-warranty-claim")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class FranchiseWarrantyClaimController {

	@Autowired
	private FranchiseWarrantyClaimService franchiseWarrantyClaimService;

	// Save a new Warranty Claim
	@PostMapping("/save")
	public ResponseEntity<FranchiseWarrantyClaim> saveWarrantyClaim(@RequestBody FranchiseWarrantyClaim claim) {
		try {
			FranchiseWarrantyClaim savedClaim = franchiseWarrantyClaimService.saveWarrantyClaim(claim);
			return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	// Retrieve all Warranty Claims
	@GetMapping("/getall")
	public ResponseEntity<List<FranchiseWarrantyClaim>> getAllWarrantyClaims() {
		try {
			List<FranchiseWarrantyClaim> claims = franchiseWarrantyClaimService.getAllWarrantyClaims();
			if (claims.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(claims, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Retrieve a Warranty Claim by ID
	@GetMapping("/get/{id}")
	public ResponseEntity<FranchiseWarrantyClaim> getWarrantyClaimById(@PathVariable("id") Long id) {
		Optional<FranchiseWarrantyClaim> claim = franchiseWarrantyClaimService.getWarrantyClaimById(id);
		return claim.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Update an existing Warranty Claim
	@PutMapping("/update/{id}")
	public ResponseEntity<FranchiseWarrantyClaim> updateWarrantyClaim(@PathVariable("id") Long id,
			@RequestBody FranchiseWarrantyClaim claim) {
		try {
			FranchiseWarrantyClaim updatedClaim = franchiseWarrantyClaimService.updateWarrantyClaim(id, claim);
			return new ResponseEntity<>(updatedClaim, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	// Delete a Warranty Claim
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteWarrantyClaim(@PathVariable("id") Long id) {
		try {
			franchiseWarrantyClaimService.deleteWarrantyClaim(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Update Warranty Claim Status
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<FranchiseWarrantyClaim> updateWarrantyClaimStatus(@PathVariable("id") Long id,
			@RequestBody Long newStatus) {
		try {
			FranchiseWarrantyClaim updatedClaim = franchiseWarrantyClaimService.updateWarrantyClaimStatus(id,
					newStatus);
			return new ResponseEntity<>(updatedClaim, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	// Get Pending Orders
	@GetMapping("/getPendingOrders")
	public ResponseEntity<List<FranchiseWarrantyClaim>> getPendingOrders() {
		List<FranchiseWarrantyClaim> pending = franchiseWarrantyClaimService.getPendingOrders();
		return new ResponseEntity<>(pending, HttpStatus.OK);
	}

	// Get Accepted Orders
	@GetMapping("/getAcceptedOrders")
	public ResponseEntity<List<FranchiseWarrantyClaim>> getAcceptedOrders() {
		List<FranchiseWarrantyClaim> accepted = franchiseWarrantyClaimService.getAcceptedOrders();
		return new ResponseEntity<>(accepted, HttpStatus.OK);
	}

	// Get Rejected Orders
	@GetMapping("/getRejectedOrders")
	public ResponseEntity<List<FranchiseWarrantyClaim>> getRejectedOrders() {
		List<FranchiseWarrantyClaim> rejected = franchiseWarrantyClaimService.getRejectedOrders();
		return new ResponseEntity<>(rejected, HttpStatus.OK);
	}

	// Get Ship Orders
	@GetMapping("/getShipOrders")
	public ResponseEntity<List<FranchiseWarrantyClaim>> getShipOrders() {
		List<FranchiseWarrantyClaim> shipped = franchiseWarrantyClaimService.getShipOrders();
		return new ResponseEntity<>(shipped, HttpStatus.OK);
	}
}
