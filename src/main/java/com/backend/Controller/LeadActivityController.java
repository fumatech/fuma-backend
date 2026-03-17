package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.LeadActivity;
import com.backend.Service.LeadActivityService;

@RestController
@CrossOrigin("*")
@RequestMapping("/lead-activity")
public class LeadActivityController {

    @Autowired
    private LeadActivityService leadActivityService;

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<LeadActivity>> getLeadActivities(@PathVariable Long leadId) {
        List<LeadActivity> activities = leadActivityService.getActivitiesByLeadId(leadId);
        return ResponseEntity.ok(activities);
    }
}
