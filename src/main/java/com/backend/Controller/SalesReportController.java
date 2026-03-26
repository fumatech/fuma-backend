package com.backend.Controller;

import com.backend.DTO.SalesReportDTO;
import com.backend.Service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    @GetMapping("/sales-performance")
    public ResponseEntity<SalesReportDTO> getSalesPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long salespersonId,
            @RequestParam(required = false) String region) {
        
        return ResponseEntity.ok(salesReportService.getSalesPerformanceReport(startDate, endDate, salespersonId, region));
    }

    @GetMapping("/lead-conversion")
    public ResponseEntity<SalesReportDTO> getLeadConversion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        return ResponseEntity.ok(salesReportService.getLeadConversionReport(startDate, endDate));
    }

    @GetMapping("/revenue")
    public ResponseEntity<SalesReportDTO> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long salespersonId) {
        
        return ResponseEntity.ok(salesReportService.getRevenueReport(startDate, endDate, salespersonId));
    }
}
