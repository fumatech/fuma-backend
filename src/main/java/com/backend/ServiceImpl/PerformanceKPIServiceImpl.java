package com.backend.ServiceImpl;

import com.backend.Entity.PerformanceKPI;
import com.backend.Repository.PerformanceKPIRepo;
import com.backend.Service.PerformanceKPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceKPIServiceImpl implements PerformanceKPIService {

    private final PerformanceKPIRepo kpiRepo;

    @Autowired
    public PerformanceKPIServiceImpl(PerformanceKPIRepo kpiRepo) {
        this.kpiRepo = kpiRepo;
    }

    @Override
    public PerformanceKPI save(PerformanceKPI kpi) {
        return kpiRepo.save(kpi);
    }

    @Override
    public List<PerformanceKPI> getAll() {
        return kpiRepo.findAll();
    }

    @Override
    public PerformanceKPI getById(Long id) {
        return kpiRepo.findById(id).orElse(null);
    }

    @Override
    public List<PerformanceKPI> getByEmployeeId(Long employeeId) {
        return kpiRepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<PerformanceKPI> getByMonthAndYear(int month, int year) {
        return kpiRepo.findByMonthAndYear(month, year);
    }

    @Override
    public List<PerformanceKPI> getByYear(int year) {
        return kpiRepo.findByYear(year);
    }

    @Override
    public List<PerformanceKPI> getByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year) {
        return kpiRepo.findByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    @Override
    public PerformanceKPI update(Long id, PerformanceKPI updatedKPI) {
        Optional<PerformanceKPI> optional = kpiRepo.findById(id);
        if (optional.isPresent()) {
            PerformanceKPI existing = optional.get();
            existing.setEmployeeId(updatedKPI.getEmployeeId());
            existing.setCategory(updatedKPI.getCategory());
            existing.setGoal(updatedKPI.getGoal());
            existing.setTargetValue(updatedKPI.getTargetValue());
            existing.setAchievedValue(updatedKPI.getAchievedValue());
            existing.setMonth(updatedKPI.getMonth());
            existing.setYear(updatedKPI.getYear());
            existing.setNotes(updatedKPI.getNotes());
            existing.setCreatedBy(updatedKPI.getCreatedBy());
            return kpiRepo.save(existing);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<PerformanceKPI> optional = kpiRepo.findById(id);
        if (optional.isPresent()) {
            kpiRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
