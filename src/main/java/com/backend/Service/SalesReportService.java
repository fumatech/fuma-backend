package com.backend.Service;

import com.backend.DTO.SalesReportDTO;
import java.time.LocalDate;

public interface SalesReportService {
    SalesReportDTO getSalesPerformanceReport(LocalDate startDate, LocalDate endDate, Long salespersonId, String region);
    SalesReportDTO getLeadConversionReport(LocalDate startDate, LocalDate endDate);
    SalesReportDTO getRevenueReport(LocalDate startDate, LocalDate endDate, Long salespersonId);
}
