package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Sale;

public interface SaleService {

    // Create a new Sale
    Sale createSale(Sale sale);

    // Retrieve all Sales
    List<Sale> getAllSales();

    // Retrieve a Sale by ID
    Optional<Sale> getSaleById(Long id);

    // Update an existing Sale
    Sale updateSale(Long id, Sale sale);

    // Delete a Sale by ID
    void deleteSale(Long id);
}
