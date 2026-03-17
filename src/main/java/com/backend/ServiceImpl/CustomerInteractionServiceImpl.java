package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.CustomerInteraction;
import com.backend.Repository.CustomerInteractionRepo;
import com.backend.Service.CustomerInteractionService;

@Service
public class CustomerInteractionServiceImpl implements CustomerInteractionService {

    @Autowired
    private CustomerInteractionRepo interactionRepo;

    @Override
    public CustomerInteraction saveInteraction(CustomerInteraction interaction) {
        return interactionRepo.save(interaction);
    }

    @Override
    public List<CustomerInteraction> getInteractionsByLeadId(Long leadId) {
        return interactionRepo.findByLeadIdOrderByInteractionDateDesc(leadId);
    }

    @Override
    public List<CustomerInteraction> getInteractionsByCustomerId(Long customerId) {
        return interactionRepo.findByCustomerIdOrderByInteractionDateDesc(customerId);
    }

    @Override
    public List<CustomerInteraction> getInteractionsBySalespersonId(Long salespersonId) {
        return interactionRepo.findBySalespersonIdOrderByInteractionDateDesc(salespersonId);
    }

    @Override
    public List<CustomerInteraction> getAllInteractions() {
        return interactionRepo.findAll();
    }
}
