package com.backend.Service;

import java.util.List;
import java.util.Optional;
import com.backend.Entity.Purchase;

public interface PurchaseService {
    
    // Create a new Purchase
    Purchase savePurchase(Purchase purchase);
    
    // Get all Purchases
    List<Purchase> getAllPurchases();
    
    // Get a Purchase by ID
    Optional<Purchase> getPurchaseById(Long id);
    
    // Update an existing Purchase
    Purchase updatePurchase(Long id, Purchase purchase);
    
    // Delete a Purchase by ID
    void deletePurchase(Long id);
}
