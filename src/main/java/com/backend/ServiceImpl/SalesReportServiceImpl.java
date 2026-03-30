package com.backend.ServiceImpl;

import com.backend.DTO.SalesReportDTO;
import com.backend.Entity.Lead;
import com.backend.Entity.LeadActivity;
import com.backend.Repository.LeadActivityRepo;
import com.backend.Repository.LeadRepo;
import com.backend.Service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Month;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesReportServiceImpl implements SalesReportService {

    @Autowired
    private LeadRepo leadRepo;

    @Autowired
    private LeadActivityRepo leadActivityRepo;

    @Override
    public SalesReportDTO getSalesPerformanceReport(LocalDate startDate, LocalDate endDate, Long salespersonId, String region) {
        SalesReportDTO report = new SalesReportDTO();
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        // 1. Lead Metrics
        List<Lead> leads = leadRepo.findByAddedOnBetween(startDateTime, endDateTime);
        if (salespersonId != null) {
            leads = leads.stream()
                    .filter(lead -> Objects.equals(lead.getEmployeeid(), salespersonId))
                    .collect(Collectors.toList());
        }
        long totalLeads = leads.size();
        long convertedLeadsCount = leads.stream()
                .filter(lead -> "CONVERTED".equalsIgnoreCase(String.valueOf(lead.getStage())))
                .count();
        
        report.setTotalLeads(totalLeads);
        report.setConvertedLeads(convertedLeadsCount);
        report.setConversionRate(totalLeads > 0 ? (double) convertedLeadsCount / totalLeads * 100 : 0);

        // 2. Average Response Time
        double totalHours = 0;
        int leadsWithActivities = 0;
        
        for (Lead lead : leads) {
            Optional<LeadActivity> firstActivity = leadActivityRepo.findFirstByLeadIdOrderByDateAsc(lead.getId());
            if (firstActivity.isPresent()) {
                Duration duration = Duration.between(lead.getAddedOn(), firstActivity.get().getDate());
                totalHours += duration.toHours();
                leadsWithActivities++;
            }
        }
        report.setAverageResponseTimeHours(leadsWithActivities > 0 ? totalHours / leadsWithActivities : 0);

        // 3. Revenue Metrics (Option 1): from all leads dealValue
        List<Lead> revenueLeads = leads.stream()
                .filter(lead -> lead.getDealValue() != null)
                .collect(Collectors.toList());

        BigDecimal totalRevenue = revenueLeads.stream()
                .map(lead -> BigDecimal.valueOf(lead.getDealValue() == null ? 0d : lead.getDealValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.setTotalRevenue(totalRevenue);

        // 4. Chart Data: Monthly Sales Trend (from all lead dealValue)
        report.setMonthlySalesTrend(generateMonthlySalesTrend(revenueLeads));

        // 5. Chart Data: Salesperson Comparison (from all lead dealValue)
        report.setSalespersonComparison(generateSalespersonComparison(revenueLeads));

        return report;
    }

    private List<Map<String, Object>> generateMonthlySalesTrend(List<Lead> revenueLeads) {
        Map<String, BigDecimal> monthlyRevenue = new TreeMap<>();

        for (Lead lead : revenueLeads) {
            if (lead.getAddedOn() == null) {
                continue;
            }
            Month monthValue = lead.getAddedOn().getMonth();
            String month = monthValue.toString();
            BigDecimal value = BigDecimal.valueOf(lead.getDealValue() == null ? 0d : lead.getDealValue());
            monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, BigDecimal.ZERO).add(value));
        }

        return monthlyRevenue.entrySet().stream().map(entry -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", entry.getKey());
            map.put("revenue", entry.getValue());
            return map;
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> generateSalespersonComparison(List<Lead> revenueLeads) {
        Map<String, BigDecimal> salespersonRevenue = new HashMap<>();

        for (Lead lead : revenueLeads) {
            String seller = lead.getEmployeeid() != null ? String.valueOf(lead.getEmployeeid()) : "Unknown";
            BigDecimal value = BigDecimal.valueOf(lead.getDealValue() == null ? 0d : lead.getDealValue());
            salespersonRevenue.put(seller, salespersonRevenue.getOrDefault(seller, BigDecimal.ZERO).add(value));
        }

        return salespersonRevenue.entrySet().stream().map(entry -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", entry.getKey());
            map.put("revenue", entry.getValue());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public SalesReportDTO getLeadConversionReport(LocalDate startDate, LocalDate endDate) {
        return getSalesPerformanceReport(startDate, endDate, null, null);
    }

    @Override
    public SalesReportDTO getRevenueReport(LocalDate startDate, LocalDate endDate, Long salespersonId) {
        return getSalesPerformanceReport(startDate, endDate, salespersonId, null);
    }

}
