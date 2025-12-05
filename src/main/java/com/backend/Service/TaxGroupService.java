package com.backend.Service;

import java.util.List;

import com.backend.Entity.TaxGroup;

public interface TaxGroupService {
    List<TaxGroup> getAllTaxGroups();
    TaxGroup createTaxGroup(TaxGroup taxGroup);
    TaxGroup updateTaxGroup(Long id, TaxGroup taxGroup); // Add update method
    void deleteTaxGroup(Long id); // Add delete method
}