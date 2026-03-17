package com.backend.ServiceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Lead;
import com.backend.Repository.LeadRepo;
import com.backend.Service.LeadService;
import com.backend.Service.LeadActivityService;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepo leadRepo;

    @Autowired
    private LeadActivityService leadActivityService;

    @Override
    public Lead saveLead(Lead lead) {
        Lead savedLead = leadRepo.save(lead);
        leadActivityService.logActivity(savedLead.getId(), "LEAD_CREATED", "Lead was created");
        return savedLead;
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
            existingLead.setCompany(updatedLead.getCompany());
            existingLead.setPhone(updatedLead.getPhone());
            existingLead.setStage(updatedLead.getStage());
            existingLead.setPriority(updatedLead.getPriority());
            existingLead.setDealValue(updatedLead.getDealValue());
            existingLead.setNextAction(updatedLead.getNextAction());
            existingLead.setFollowUpDate(updatedLead.getFollowUpDate());
            existingLead.setFollowUpNote(updatedLead.getFollowUpNote());
            existingLead.setFollowUpStatus(updatedLead.getFollowUpStatus());
            
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

    @Override
    public Lead updateLeadStage(Long id, String stage) {
        Optional<Lead> optionalLead = leadRepo.findById(id);
        if (optionalLead.isPresent()) {
            Lead existingLead = optionalLead.get();
            String oldStage = existingLead.getStage();
            existingLead.setStage(stage);
            existingLead.setUpdatedAt(LocalDateTime.now());
            Lead savedLead = leadRepo.save(existingLead);
            if (oldStage == null || !stage.equals(oldStage)) {
                leadActivityService.logActivity(id, "STAGE_UPDATED", "Stage moved from " + oldStage + " to " + stage);
            }
            return savedLead;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> getLeadAnalytics() {
        long totalLeads = leadRepo.count();
        long convertedLeads = leadRepo.countByStage("CONVERTED");
        long lostLeads = leadRepo.countByStage("LOST");
        double conversionRate = totalLeads > 0
                ? Math.round((double) convertedLeads / totalLeads * 100.0)
                : 0;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalLeads", totalLeads);
        analytics.put("convertedLeads", convertedLeads);
        analytics.put("lostLeads", lostLeads);
        analytics.put("conversionRate", conversionRate);

        return analytics;
    }

    @Override
    public List<Lead> getTodaysFollowUps() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return leadRepo.findFollowUpsInRange(startOfDay, endOfDay, "PENDING");
    }

    @Override
    public List<Lead> getUpcomingFollowUps() {
        LocalDateTime tomorrowStart = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return leadRepo.findByFollowUpDateAfterAndFollowUpStatus(tomorrowStart, "PENDING");
    }

    @Override
    public List<Lead> getOverdueFollowUps() {
        return leadRepo.findOverdueFollowUps(LocalDateTime.now());
    }
}
