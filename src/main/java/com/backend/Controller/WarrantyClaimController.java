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
import com.backend.Entity.WarrantyClaim;
import com.backend.Service.WarrantyClaimService;

@RestController
@RequestMapping("/warranty-claim")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class WarrantyClaimController {

    @Autowired
    private WarrantyClaimService warrantyClaimService;

    // Save a new Warranty Claim
    @PostMapping("/save")
    public ResponseEntity<WarrantyClaim> saveWarrantyClaim(@RequestBody WarrantyClaim warrantyClaim) {
        try {
            WarrantyClaim savedWarrantyClaim = warrantyClaimService.saveWarrantyClaim(warrantyClaim);
            return new ResponseEntity<>(savedWarrantyClaim, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all Warranty Claims
    @GetMapping("/getall")
    public ResponseEntity<List<WarrantyClaim>> getAllWarrantyClaims() {
        try {
            List<WarrantyClaim> warrantyClaims = warrantyClaimService.getAllWarrantyClaims();
            if (warrantyClaims.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(warrantyClaims, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a Warranty Claim by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<WarrantyClaim> getWarrantyClaimById(@PathVariable("id") Long id) {
        Optional<WarrantyClaim> warrantyClaim = warrantyClaimService.getWarrantyClaimById(id);
        return warrantyClaim.map(
                claim -> new ResponseEntity<>(claim, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Warranty Claim
    @PutMapping("/update/{id}")
    public ResponseEntity<WarrantyClaim> updateWarrantyClaim(
            @PathVariable("id") Long id, @RequestBody WarrantyClaim warrantyClaim) {
        try {
            WarrantyClaim updatedWarrantyClaim = warrantyClaimService.updateWarrantyClaim(id, warrantyClaim);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Warranty Claim by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWarrantyClaim(@PathVariable("id") Long id) {
        try {
            warrantyClaimService.deleteWarrantyClaim(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
 // Update the status of a Warranty Claim by ID
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<WarrantyClaim> updateWarrantyClaimStatus(
            @PathVariable("id") Long id, @RequestBody Long newStatus) {
        try {
            WarrantyClaim updatedWarrantyClaim = warrantyClaimService.updateWarrantyClaimStatus(id, newStatus);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    
}
