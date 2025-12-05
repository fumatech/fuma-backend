package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Leave;
import com.backend.Repository.LeaveRepo;
import com.backend.Service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Override
    public Leave saveLeave(Leave leave) {
        return leaveRepo.save(leave);  // Save the leave entity to the database
    }

    @Override
    public List<Leave> getAllLeaves() {
        return leaveRepo.findAll();  // Retrieve all leave entities from the database
    }

    @Override
    public Leave updateLeave(Long id, Leave updatedLeave) {
        Optional<Leave> existingLeave = leaveRepo.findById(id);
        if (existingLeave.isPresent()) {
            Leave leave = existingLeave.get();
            leave.setType(updatedLeave.getType());
            leave.setMaxCount(updatedLeave.getMaxCount());
            leave.setLeaveInterval(updatedLeave.getLeaveInterval());
            return leaveRepo.save(leave);  // Save the updated leave entity
        }
        return null;  // Return null if the leave doesn't exist
    }

    @Override
    public Leave getLeaveById(Long id) {
        return leaveRepo.findById(id).orElse(null);  // Retrieve the leave entity by ID
    }

    @Override
    public void deleteLeaveById(Long id) {
        leaveRepo.deleteById(id);  // Delete the leave entity by ID
    }
}
