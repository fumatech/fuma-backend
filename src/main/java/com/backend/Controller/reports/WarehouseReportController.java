package com.backend.Controller.reports;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.backend.Service.reports.WarehouseAnalyticsService;

@RestController
@RequestMapping("/api/reports/warehouse")
@CrossOrigin(
        origins = {
                "http://localhost:3000",
                "http://fusionmastertech.com",
                "https://fusionmastertech.com",
                "http://www.fusionmastertech.com",
                "https://www.fusionmastertech.com"
        },
        allowCredentials = "true"
)
public class WarehouseReportController {
    @Autowired
    private WarehouseAnalyticsService analyticsService;

    // GET /api/reports/warehouse/{id}/stock
    @GetMapping("/{id}/stock")
    public Object getStockReport(@PathVariable Long id,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false) String date,
                                 @RequestParam(required = false) String status) {
        return analyticsService.getStockReport(id, category, date, status);
    }

    // GET /api/reports/warehouse/{id}/movements
    @GetMapping("/{id}/movements")
    public Object getMovementLogs(@PathVariable Long id) {
        return analyticsService.getMovementLogs(id);
    }

    // GET /api/reports/warehouse/{id}/aging
    @GetMapping("/{id}/aging")
    public Object getAgingReport(@PathVariable Long id) {
        return analyticsService.getAgingReport(id);
    }

    // GET /api/reports/warehouse/{id}/performance
    @GetMapping("/{id}/performance")
    public Object getPerformanceMetrics(@PathVariable Long id) {
        return analyticsService.getPerformanceMetrics(id);
    }

    // GET /api/reports/warehouse/{id}/turnover
    @GetMapping("/{id}/turnover")
    public Object getTurnoverAnalysis(@PathVariable Long id) {
        return analyticsService.getTurnoverAnalysis(id);
    }
}
