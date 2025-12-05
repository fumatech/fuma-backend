package com.backend.Service;

import java.util.List;

import com.backend.Entity.TaxRate;

public interface TaxRateService {

    // Fetch all TaxRates
    List<TaxRate> getAllTaxRates();
    
    // Fetch a specific TaxRate by its ID
    TaxRate getTaxRateById(Long id);
    
    // Create a new TaxRate
    TaxRate createTaxRate(TaxRate taxRate);
    
    // Update an existing TaxRate by its ID
    TaxRate updateTaxRate(Long id, TaxRate taxRate);
    
    // Delete a TaxRate by its ID
    void deleteTaxRate(Long id);
    
    // Fetch multiple TaxRates based on a list of IDs (needed for linking TaxRates to TaxGroups)
    List<TaxRate> getTaxRatesByIds(List<Long> taxRateIds);
}
