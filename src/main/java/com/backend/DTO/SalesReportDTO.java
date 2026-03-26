package com.backend.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SalesReportDTO {
    private long totalLeads;
    private long convertedLeads;
    private double conversionRate;
    private double averageResponseTimeHours;
    private BigDecimal totalRevenue;
    
    // For Charts
    private List<Map<String, Object>> monthlySalesTrend;
    private List<Map<String, Object>> salespersonComparison;
    private List<Map<String, Object>> leadConversionStatus;

    public SalesReportDTO() {}

    public long getTotalLeads() {
        return totalLeads;
    }

    public void setTotalLeads(long totalLeads) {
        this.totalLeads = totalLeads;
    }

    public long getConvertedLeads() {
        return convertedLeads;
    }

    public void setConvertedLeads(long convertedLeads) {
        this.convertedLeads = convertedLeads;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public double getAverageResponseTimeHours() {
        return averageResponseTimeHours;
    }

    public void setAverageResponseTimeHours(double averageResponseTimeHours) {
        this.averageResponseTimeHours = averageResponseTimeHours;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<Map<String, Object>> getMonthlySalesTrend() {
        return monthlySalesTrend;
    }

    public void setMonthlySalesTrend(List<Map<String, Object>> monthlySalesTrend) {
        this.monthlySalesTrend = monthlySalesTrend;
    }

    public List<Map<String, Object>> getSalespersonComparison() {
        return salespersonComparison;
    }

    public void setSalespersonComparison(List<Map<String, Object>> salespersonComparison) {
        this.salespersonComparison = salespersonComparison;
    }

    public List<Map<String, Object>> getLeadConversionStatus() {
        return leadConversionStatus;
    }

    public void setLeadConversionStatus(List<Map<String, Object>> leadConversionStatus) {
        this.leadConversionStatus = leadConversionStatus;
    }
}
