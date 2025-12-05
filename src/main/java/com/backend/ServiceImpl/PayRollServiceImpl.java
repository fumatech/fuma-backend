package com.backend.ServiceImpl;

import com.backend.Entity.PayRoll;
import com.backend.Repository.PayRollRepo;
import com.backend.Service.PayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayRollServiceImpl implements PayRollService {

    @Autowired
    private PayRollRepo payRollRepo;

    @Override
    public PayRoll savePayRoll(PayRoll payRoll) {
        return payRollRepo.save(payRoll);
    }

    @Override
    public List<PayRoll> getAllPayRolls() {
        return payRollRepo.findAll();
    }

    @Override
    public PayRoll getPayRollById(Long id) {
        return payRollRepo.findById(id).orElse(null);
    }

    @Override
    public PayRoll updatePayRoll(Long id, PayRoll updatedPayRoll) {
        Optional<PayRoll> existingOptional = payRollRepo.findById(id);
        if (existingOptional.isPresent()) {
            PayRoll existing = existingOptional.get();

            existing.setLocation(updatedPayRoll.getLocation());
            existing.setMonthYear(updatedPayRoll.getMonthYear());
            existing.setStatus(updatedPayRoll.getStatus());

            // Replace employeePayrolls entirely (or handle more gracefully if needed)
            existing.getEmployeePayrolls().clear();
            existing.getEmployeePayrolls().addAll(updatedPayRoll.getEmployeePayrolls());

            return payRollRepo.save(existing);
        } else {
            return null;
        }
    }

    @Override
    public void deletePayRollById(Long id) {
        payRollRepo.deleteById(id);
    }
}
