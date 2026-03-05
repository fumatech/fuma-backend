package com.backend.Controller;

import com.backend.Entity.PerformanceKPI;
import com.backend.Service.PerformanceKPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance-kpi")
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
public class PerformanceKPIController {

    private final PerformanceKPIService kpiService;

    @Autowired
    public PerformanceKPIController(PerformanceKPIService kpiService) {
        this.kpiService = kpiService;
    }

    // Add a new KPI
    @PostMapping("/add")
    public ResponseEntity<PerformanceKPI> addKPI(@RequestBody PerformanceKPI kpi) {
        PerformanceKPI created = kpiService.save(kpi);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all KPIs
    @GetMapping("/getall")
    public ResponseEntity<List<PerformanceKPI>> getAllKPIs() {
        List<PerformanceKPI> kpis = kpiService.getAll();
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }

    // Get KPI by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<PerformanceKPI> getKPIById(@PathVariable("id") Long id) {
        PerformanceKPI kpi = kpiService.getById(id);
        if (kpi == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kpi, HttpStatus.OK);
    }

    // Get KPIs by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceKPI>> getByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        List<PerformanceKPI> kpis = kpiService.getByEmployeeId(employeeId);
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }

    // Get KPIs by month and year
    @GetMapping("/period/{month}/{year}")
    public ResponseEntity<List<PerformanceKPI>> getByMonthAndYear(
            @PathVariable("month") int month,
            @PathVariable("year") int year) {
        List<PerformanceKPI> kpis = kpiService.getByMonthAndYear(month, year);
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }

    // Get KPIs by year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<PerformanceKPI>> getByYear(@PathVariable("year") int year) {
        List<PerformanceKPI> kpis = kpiService.getByYear(year);
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }

    // Get KPIs by employee, month and year
    @GetMapping("/employee/{employeeId}/{month}/{year}")
    public ResponseEntity<List<PerformanceKPI>> getByEmployeeAndPeriod(
            @PathVariable("employeeId") Long employeeId,
            @PathVariable("month") int month,
            @PathVariable("year") int year) {
        List<PerformanceKPI> kpis = kpiService.getByEmployeeIdAndMonthAndYear(employeeId, month, year);
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }

    // Update a KPI
    @PutMapping("/update/{id}")
    public ResponseEntity<PerformanceKPI> updateKPI(@PathVariable("id") Long id, @RequestBody PerformanceKPI updatedKPI) {
        PerformanceKPI kpi = kpiService.update(id, updatedKPI);
        if (kpi == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kpi, HttpStatus.OK);
    }

    // Delete a KPI
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteKPI(@PathVariable("id") Long id) {
        boolean isDeleted = kpiService.deleteById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
