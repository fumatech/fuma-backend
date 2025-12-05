package com.backend.Controller;

import com.backend.Entity.Purchase;
import com.backend.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchase")
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
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // Create a new Purchase
    @PostMapping("/save")
    public ResponseEntity<Purchase> createPurchase(@RequestBody Purchase purchase) {
        Purchase savedPurchase = purchaseService.savePurchase(purchase);
        return new ResponseEntity<>(savedPurchase, HttpStatus.CREATED);
    }

    // Get all Purchases
    @GetMapping("/getall")
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    // Get a Purchase by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Purchase>> getPurchaseById(@PathVariable Long id) {
        Optional<Purchase> purchase = purchaseService.getPurchaseById(id);
        return purchase.isPresent() ? 
            new ResponseEntity<>(purchase, HttpStatus.OK) : 
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update a Purchase
    @PutMapping("/update/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchaseDetails) {
        Purchase updatedPurchase = purchaseService.updatePurchase(id, purchaseDetails);
        return new ResponseEntity<>(updatedPurchase, HttpStatus.OK);
    }

    // Delete a Purchase by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
