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
import com.backend.Entity.VendorWarrantyClaim;
import com.backend.Service.VendorWarrantyClaimService;

@RestController
@RequestMapping("/vendor-warranty-claim")
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
public class VendorWarrantyClaimController {

    @Autowired
    private VendorWarrantyClaimService vendorWarrantyClaimService;

    // Save a new Warranty Claim
    @PostMapping("/save")
    public ResponseEntity<VendorWarrantyClaim> saveWarrantyClaim(@RequestBody VendorWarrantyClaim vendorWarrantyClaim) {
        try {
        	VendorWarrantyClaim savedWarrantyClaim = vendorWarrantyClaimService.saveWarrantyClaim(vendorWarrantyClaim);
            return new ResponseEntity<>(savedWarrantyClaim, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all Warranty Claims
    @GetMapping("/getall")
    public ResponseEntity<List<VendorWarrantyClaim>> getAllWarrantyClaims() {
        try {
            List<VendorWarrantyClaim> warrantyClaims = vendorWarrantyClaimService.getAllWarrantyClaims();
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
    public ResponseEntity<VendorWarrantyClaim> getWarrantyClaimById(@PathVariable("id") Long id) {
        Optional<VendorWarrantyClaim> warrantyClaim = vendorWarrantyClaimService.getWarrantyClaimById(id);
        return warrantyClaim.map(
                claim -> new ResponseEntity<>(claim, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Warranty Claim
    @PutMapping("/update/{id}")
    public ResponseEntity<VendorWarrantyClaim> updateWarrantyClaim(
            @PathVariable("id") Long id, @RequestBody VendorWarrantyClaim vendorWarrantyClaim) {
        try {
        	VendorWarrantyClaim updatedWarrantyClaim = vendorWarrantyClaimService.updateWarrantyClaim(id, vendorWarrantyClaim);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWarrantyClaim(@PathVariable("id") Long id) {
        try {
            vendorWarrantyClaimService.deleteWarrantyClaim(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<VendorWarrantyClaim> updateWarrantyClaimStatus(
            @PathVariable("id") Long id, @RequestBody Long newStatus) {
        try {
        	VendorWarrantyClaim updatedWarrantyClaim = vendorWarrantyClaimService.updateWarrantyClaimStatus(id, newStatus);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/getPendingOrders")
    public ResponseEntity<List<VendorWarrantyClaim>> getPendingOrders() {
        List<VendorWarrantyClaim> pendingOrders = vendorWarrantyClaimService.getPendingOrders();
        return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
    }

    @GetMapping("/getAcceptedOrders")
    public ResponseEntity<List<VendorWarrantyClaim>> getAcceptedOrders() {
        List<VendorWarrantyClaim> acceptedOrders = vendorWarrantyClaimService.getAcceptedOrders();
        return new ResponseEntity<>(acceptedOrders, HttpStatus.OK);
    }
    
    @GetMapping("/getRejectedOrders")
    public ResponseEntity<List<VendorWarrantyClaim>> getRejectedOrders() {
        List<VendorWarrantyClaim> rejectedOrders = vendorWarrantyClaimService.getRejectedOrders();
        return new ResponseEntity<>(rejectedOrders, HttpStatus.OK);
    }
    
    @GetMapping("/getShipOrders")
    public ResponseEntity<List<VendorWarrantyClaim>> getShipOrders() {
        List<VendorWarrantyClaim> shipOrders = vendorWarrantyClaimService.getShipOrders();
        return new ResponseEntity<>(shipOrders, HttpStatus.OK);
    }
    


    
}
