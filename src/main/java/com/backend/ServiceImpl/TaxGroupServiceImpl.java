package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.TaxGroup;
import com.backend.Repository.TaxGroupRepo;
import com.backend.Service.TaxGroupService;

@Service
public class TaxGroupServiceImpl implements TaxGroupService {

    @Autowired
    private TaxGroupRepo taxGroupRepo;



    @Override
    public List<TaxGroup> getAllTaxGroups() {
        return taxGroupRepo.findAll();
    }

    @Override
    public TaxGroup createTaxGroup(TaxGroup taxGroup) {
        return taxGroupRepo.save(taxGroup);
    }

    @Override
    public TaxGroup updateTaxGroup(Long id, TaxGroup taxGroup) {
        if (taxGroupRepo.existsById(id)) {
            taxGroup.setId(id); // Ensuring the correct ID is set
            return taxGroupRepo.save(taxGroup);
        }
        return null;
    }

    @Override
    public void deleteTaxGroup(Long id) {
        if (taxGroupRepo.existsById(id)) {
            taxGroupRepo.deleteById(id);
        }
    }
}
