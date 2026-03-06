package com.backend.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.EmployeeGrievance;
import com.backend.Repository.EmployeeGrievanceRepo;
import com.backend.Service.EmployeeGrievanceService;

@Service
public class EmployeeGrievanceServiceImpl implements EmployeeGrievanceService {

    private final EmployeeGrievanceRepo grievanceRepo;

    @Autowired
    public EmployeeGrievanceServiceImpl(EmployeeGrievanceRepo grievanceRepo) {
        this.grievanceRepo = grievanceRepo;
    }

    @Override
    public EmployeeGrievance save(EmployeeGrievance grievance) {
        return grievanceRepo.save(grievance);
    }

    @Override
    public List<EmployeeGrievance> getAll() {
        return grievanceRepo.findAll();
    }

    @Override
    public EmployeeGrievance getById(Long id) {
        return grievanceRepo.findById(id).orElse(null);
    }

    @Override
    public List<EmployeeGrievance> getByEmployeeId(Long employeeId) {
        return grievanceRepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeGrievance> getByStatus(String status) {
        return grievanceRepo.findByStatus(status);
    }

    @Override
    public List<EmployeeGrievance> getByCategory(String category) {
        return grievanceRepo.findByCategory(category);
    }

    @Override
    public List<EmployeeGrievance> getByPriority(String priority) {
        return grievanceRepo.findByPriority(priority);
    }

    @Override
    public List<EmployeeGrievance> getByAssignedTo(Long assignedTo) {
        return grievanceRepo.findByAssignedTo(assignedTo);
    }

    @Override
    public List<EmployeeGrievance> getByFeedbackType(String feedbackType) {
        return grievanceRepo.findByFeedbackType(feedbackType);
    }

    @Override
    public EmployeeGrievance update(Long id, EmployeeGrievance updatedGrievance) {
        Optional<EmployeeGrievance> optional = grievanceRepo.findById(id);
        if (optional.isPresent()) {
            EmployeeGrievance existing = optional.get();
            existing.setEmployeeId(updatedGrievance.getEmployeeId());
            existing.setAnonymous(updatedGrievance.isAnonymous());
            existing.setCategory(updatedGrievance.getCategory());
            existing.setPriority(updatedGrievance.getPriority());
            existing.setSubject(updatedGrievance.getSubject());
            existing.setDescription(updatedGrievance.getDescription());
            existing.setStatus(updatedGrievance.getStatus());
            existing.setResolution(updatedGrievance.getResolution());
            existing.setAssignedTo(updatedGrievance.getAssignedTo());
            existing.setEscalatedTo(updatedGrievance.getEscalatedTo());
            existing.setEscalationNotes(updatedGrievance.getEscalationNotes());
            existing.setFeedbackType(updatedGrievance.getFeedbackType());
            existing.setSatisfactionRating(updatedGrievance.getSatisfactionRating());
            existing.setEmployeeRemarks(updatedGrievance.getEmployeeRemarks());

            if ("Resolved".equals(updatedGrievance.getStatus()) && existing.getResolvedAt() == null) {
                existing.setResolvedAt(LocalDateTime.now());
            }
            if ("Escalated".equals(updatedGrievance.getStatus()) && existing.getEscalatedAt() == null) {
                existing.setEscalatedAt(LocalDateTime.now());
            }

            return grievanceRepo.save(existing);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<EmployeeGrievance> optional = grievanceRepo.findById(id);
        if (optional.isPresent()) {
            grievanceRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
