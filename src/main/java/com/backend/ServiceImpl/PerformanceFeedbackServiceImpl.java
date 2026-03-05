package com.backend.ServiceImpl;

import com.backend.Entity.PerformanceFeedback;
import com.backend.Repository.PerformanceFeedbackRepo;
import com.backend.Service.PerformanceFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceFeedbackServiceImpl implements PerformanceFeedbackService {

    private final PerformanceFeedbackRepo feedbackRepo;

    @Autowired
    public PerformanceFeedbackServiceImpl(PerformanceFeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    @Override
    public PerformanceFeedback save(PerformanceFeedback feedback) {
        return feedbackRepo.save(feedback);
    }

    @Override
    public List<PerformanceFeedback> getAll() {
        return feedbackRepo.findAll();
    }

    @Override
    public PerformanceFeedback getById(Long id) {
        return feedbackRepo.findById(id).orElse(null);
    }

    @Override
    public List<PerformanceFeedback> getByEmployeeId(Long employeeId) {
        return feedbackRepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<PerformanceFeedback> getByMonthAndYear(int month, int year) {
        return feedbackRepo.findByMonthAndYear(month, year);
    }

    @Override
    public List<PerformanceFeedback> getByYear(int year) {
        return feedbackRepo.findByYear(year);
    }

    @Override
    public List<PerformanceFeedback> getByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year) {
        return feedbackRepo.findByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    @Override
    public PerformanceFeedback update(Long id, PerformanceFeedback updatedFeedback) {
        Optional<PerformanceFeedback> optional = feedbackRepo.findById(id);
        if (optional.isPresent()) {
            PerformanceFeedback existing = optional.get();
            existing.setEmployeeId(updatedFeedback.getEmployeeId());
            existing.setRating(updatedFeedback.getRating());
            existing.setStrengths(updatedFeedback.getStrengths());
            existing.setImprovements(updatedFeedback.getImprovements());
            existing.setComments(updatedFeedback.getComments());
            existing.setMonth(updatedFeedback.getMonth());
            existing.setYear(updatedFeedback.getYear());
            existing.setGivenBy(updatedFeedback.getGivenBy());
            return feedbackRepo.save(existing);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<PerformanceFeedback> optional = feedbackRepo.findById(id);
        if (optional.isPresent()) {
            feedbackRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
