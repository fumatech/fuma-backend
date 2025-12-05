package com.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.Entity.Tax;
import com.backend.Repository.TaxRepository;
import com.backend.Service.TaxService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }

    @Override
    public Tax getTaxById(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax not found with id: " + id));
    }

    @Override
    public Tax createTax(Tax tax) {
        return taxRepository.save(tax);
    }

    @Override
    public Tax updateTax(Long id, Tax taxDetails) {
        Tax existingTax = taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax not found with id: " + id));

        existingTax.setTaxName(taxDetails.getTaxName());
        existingTax.setTaxValue(taxDetails.getTaxValue());

        if (taxDetails.getIncludedTaxes() != null) {
            existingTax.setIncludedTaxes(taxDetails.getIncludedTaxes());
        }

        Tax updatedTax = taxRepository.save(existingTax);

        List<Tax> groupTaxes = taxRepository.findAll().stream()
                .filter(tax -> tax.getIncludedTaxes() != null && tax.getIncludedTaxes().contains(existingTax))
                .collect(Collectors.toList());

        for (Tax groupTax : groupTaxes) {
            taxRepository.save(groupTax);
        }

        return updatedTax;
    }

    @Override
    public void deleteTax(Long id) {
        Optional<Tax> taxOptional = taxRepository.findById(id);
        if (taxOptional.isEmpty()) {
            throw new RuntimeException("Tax not found with id: " + id);
        }
        
        Tax tax = taxOptional.get();

        List<Tax> groupTaxes = taxRepository.findAll().stream()
                .filter(t -> t.getIncludedTaxes() != null && t.getIncludedTaxes().contains(tax))
                .collect(Collectors.toList());

        for (Tax groupTax : groupTaxes) {
            groupTax.getIncludedTaxes().remove(tax);
            taxRepository.save(groupTax);
        }

        taxRepository.deleteById(id);
    }
}
