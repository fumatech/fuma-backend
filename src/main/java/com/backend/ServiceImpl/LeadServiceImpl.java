package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Lead;
import com.backend.Repository.LeadRepo;
import com.backend.Service.LeadService;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepo leadRepo;

    @Override
    public Lead saveLead(Lead lead) {
        return leadRepo.save(lead);
    }

    @Override
    public List<Lead> getAllLeads() {
        return leadRepo.findAll();
    }

    @Override
    public Lead updateLead(Long id, Lead updatedLead) {
        Optional<Lead> optionalLead = leadRepo.findById(id);
        if (optionalLead.isPresent()) {
            Lead existingLead = optionalLead.get();
            existingLead.setName(updatedLead.getName());
            existingLead.setEmail(updatedLead.getEmail());
            existingLead.setSource(updatedLead.getSource());
            existingLead.setLifeStage(updatedLead.getLifeStage());
            existingLead.setEmployeeid(updatedLead.getEmployeeid());
            existingLead.setMobileNumber(updatedLead.getMobileNumber());
            existingLead.setTaxNumber(updatedLead.getTaxNumber());
            existingLead.setAddedOn(updatedLead.getAddedOn());
            existingLead.setCustomField1(updatedLead.getCustomField1());
            existingLead.setCustomField2(updatedLead.getCustomField2());
            existingLead.setCustomField3(updatedLead.getCustomField3());

            return leadRepo.save(existingLead);
        } else {
            return null; // Or throw a custom exception
        }
    }

    @Override
    public Lead getLeadById(Long id) {
        return leadRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteLeadById(Long id) {
        leadRepo.deleteById(id);
    }
}
