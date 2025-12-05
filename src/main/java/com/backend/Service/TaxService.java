package com.backend.Service;

import java.util.List;

import com.backend.Entity.Tax;

public interface TaxService {
    List<Tax> getAllTaxes();
    Tax getTaxById(Long id);
    Tax createTax(Tax tax);
    Tax updateTax(Long id, Tax taxDetails);
    void deleteTax(Long id);
}

