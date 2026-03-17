package com.backend.Service;

import java.util.List;
import java.util.Map;

import com.backend.Entity.Lead;

public interface LeadService {
	
    Lead saveLead(Lead lead);
	
	List<Lead> getAllLeads();

	Lead updateLead(Long id, Lead updatedLead);

	Lead getLeadById(Long id);

	void deleteLeadById(Long id);

	Lead updateLeadStage(Long id, String stage);

	Map<String, Object> getLeadAnalytics();

	List<Lead> getTodaysFollowUps();

	List<Lead> getUpcomingFollowUps();

	List<Lead> getOverdueFollowUps();

}
