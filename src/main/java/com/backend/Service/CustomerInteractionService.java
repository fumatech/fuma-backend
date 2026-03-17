package com.backend.Service;

import java.util.List;

import com.backend.Entity.CustomerInteraction;

public interface CustomerInteractionService {

    CustomerInteraction saveInteraction(CustomerInteraction interaction);

    List<CustomerInteraction> getInteractionsByLeadId(Long leadId);

    List<CustomerInteraction> getInteractionsByCustomerId(Long customerId);

    List<CustomerInteraction> getInteractionsBySalespersonId(Long salespersonId);

    List<CustomerInteraction> getAllInteractions();
}
