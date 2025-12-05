package com.backend.Service;

import java.util.List;

import com.backend.Entity.Lead;

public interface LeadService {
	
    Lead saveLead(Lead lead);
	
	List<Lead> getAllLeads();

	Lead updateLead(Long id, Lead updatedLead);

	Lead getLeadById(Long id);

	void deleteLeadById(Long id);

}
