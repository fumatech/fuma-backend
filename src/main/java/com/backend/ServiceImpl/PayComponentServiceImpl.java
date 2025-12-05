package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PayComponent;
import com.backend.Repository.PayComponentRepo;
import com.backend.Service.PayComponentService;

@Service
public class PayComponentServiceImpl implements PayComponentService {

    @Autowired
    private PayComponentRepo payComponentRepo;

    @Override
    public PayComponent savePayComponent(PayComponent payComponent) {
        return payComponentRepo.save(payComponent);  // Saves the pay component to the database
    }

    @Override
    public List<PayComponent> getAllPayComponents() {
        return payComponentRepo.findAll();  // Fetch all pay components from the database
    }

    @Override
    public PayComponent updatePayComponent(Long id, PayComponent updatedPayComponent) {
        Optional<PayComponent> existingPayComponent = payComponentRepo.findById(id);
        
        if(existingPayComponent.isPresent()) {
            PayComponent payComponent = existingPayComponent.get();
            payComponent.setDescription(updatedPayComponent.getDescription());
            payComponent.setType(updatedPayComponent.getType());
            payComponent.setAmountType(updatedPayComponent.getAmountType());
            payComponent.setAmount(updatedPayComponent.getAmount());
            payComponent.setApplicableDate(updatedPayComponent.getApplicableDate());
            
            return payComponentRepo.save(payComponent);  // Save the updated pay component
        }
        
        return null;  // Return null if the pay component with the given ID does not exist
    }

    @Override
    public PayComponent getPayComponentById(Long id) {
        return payComponentRepo.findById(id).orElse(null);  // Fetch a specific pay component by ID
    }

    @Override
    public void deletePayComponentById(Long id) {
        payComponentRepo.deleteById(id);  // Delete the pay component with the given ID
    }
}
