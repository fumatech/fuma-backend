package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.TaxRate;
import com.backend.Repository.TaxRateRepo;
import com.backend.Service.TaxRateService;

@Service
public class TaxRateServiceImpl implements TaxRateService {

    @Autowired
    private TaxRateRepo taxRateRepo;

    @Override
    public List<TaxRate> getAllTaxRates() {
        return taxRateRepo.findAll();
    }

    @Override
    public TaxRate getTaxRateById(Long id) {
        return taxRateRepo.findById(id).orElse(null); // Return null if not found
    }

    @Override
    public TaxRate createTaxRate(TaxRate taxRate) {
        return taxRateRepo.save(taxRate);
    }

    @Override
    public TaxRate updateTaxRate(Long id, TaxRate taxRate) {
        if (taxRateRepo.existsById(id)) {
            taxRate.setId(id); // Ensure the ID stays the same during update
            return taxRateRepo.save(taxRate);
        }
        return null; // Return null if the tax rate does not exist
    }

    @Override
    public void deleteTaxRate(Long id) {
        if (taxRateRepo.existsById(id)) {
            taxRateRepo.deleteById(id);
        }
    }

    // New method to fetch multiple TaxRates based on a list of IDs
    @Override
    public List<TaxRate> getTaxRatesByIds(List<Long> taxRateIds) {
        return taxRateRepo.findAllById(taxRateIds); // Uses Spring Data JPA's method to fetch by IDs
    }
}
