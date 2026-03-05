package com.backend.Service;

import com.backend.Entity.PerformanceFeedback;

import java.util.List;

public interface PerformanceFeedbackService {

    PerformanceFeedback save(PerformanceFeedback feedback);

    List<PerformanceFeedback> getAll();

    PerformanceFeedback getById(Long id);

    List<PerformanceFeedback> getByEmployeeId(Long employeeId);

    List<PerformanceFeedback> getByMonthAndYear(int month, int year);

    List<PerformanceFeedback> getByYear(int year);

    List<PerformanceFeedback> getByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);

    PerformanceFeedback update(Long id, PerformanceFeedback updatedFeedback);

    boolean deleteById(Long id);
}
