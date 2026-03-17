package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Lead;
import com.backend.Service.LeadService;

@RestController
@RequestMapping("/lead")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/save")
    public Lead saveLead(@RequestBody Lead lead) {
        return leadService.saveLead(lead);
    }

    @GetMapping("/getall")
    public List<Lead> getAllLeads() {
        return leadService.getAllLeads();
    }

    @GetMapping("/get/{id}")
    public Lead getLeadById(@PathVariable Long id) {
        return leadService.getLeadById(id);
    }

    @PutMapping("/update/{id}")
    public Lead updateLead(@PathVariable Long id, @RequestBody Lead updatedLead) {
        return leadService.updateLead(id, updatedLead);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLead(@PathVariable Long id) {
        leadService.deleteLeadById(id);
    }

    @PutMapping("/update-stage/{id}")
    public Lead updateLeadStage(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String stage = body.get("stage");
        return leadService.updateLeadStage(id, stage);
    }

    @GetMapping("/analytics")
    public Map<String, Object> getLeadAnalytics() {
        return leadService.getLeadAnalytics();
    }

    @GetMapping("/followups/today")
    public List<Lead> getTodaysFollowUps() {
        return leadService.getTodaysFollowUps();
    }

    @GetMapping("/followups/upcoming")
    public List<Lead> getUpcomingFollowUps() {
        return leadService.getUpcomingFollowUps();
    }

    @GetMapping("/followups/overdue")
    public List<Lead> getOverdueFollowUps() {
        return leadService.getOverdueFollowUps();
    }
}
