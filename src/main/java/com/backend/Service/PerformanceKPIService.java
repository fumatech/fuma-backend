package com.backend.Service;

import com.backend.Entity.PerformanceKPI;

import java.util.List;

public interface PerformanceKPIService {

    PerformanceKPI save(PerformanceKPI kpi);

    List<PerformanceKPI> getAll();

    PerformanceKPI getById(Long id);

    List<PerformanceKPI> getByEmployeeId(Long employeeId);

    List<PerformanceKPI> getByMonthAndYear(int month, int year);

    List<PerformanceKPI> getByYear(int year);

    List<PerformanceKPI> getByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);

    PerformanceKPI update(Long id, PerformanceKPI updatedKPI);

    boolean deleteById(Long id);
}
