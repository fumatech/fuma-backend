package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.DTO.BulkPayrollTransactionRequest;
import com.backend.DTO.EmployeePayrollViewDTO;
import com.backend.DTO.PayrollResponseDTO;
import com.backend.DTO.PayrollSaveRequest;
import com.backend.Entity.Transaction;
import com.backend.Service.PayRollService;
import com.backend.ServiceImpl.PayRollServiceImpl;

@RestController
@RequestMapping("/payroll")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class PayRollController {

    @Autowired
    private PayRollService payRollService;

    @Autowired
    private PayRollServiceImpl payRollServiceImpl;

    @PostMapping("/payroll-employee/bulk-transaction/{accountId}")
    public ResponseEntity<List<Transaction>> createBulkEmployeeSalaryTransactions(@PathVariable Long accountId,
            @RequestBody List<BulkPayrollTransactionRequest> requests) {

        return ResponseEntity.ok(payRollServiceImpl.createBulkPayrollEmployeeTransactions(accountId, requests));
    }

    @PostMapping("/payroll-employee/bulk-transaction")
    public ResponseEntity<List<Transaction>> createBulkEmployeeSalaryTransactions(
            @RequestBody List<BulkPayrollTransactionRequest> requests) {

        return ResponseEntity.ok(payRollServiceImpl.createBulkPayrollEmployeeTransactions(requests));
    }

    @PostMapping("/save")
    public ResponseEntity<PayrollResponseDTO> savePayroll(@RequestBody PayrollSaveRequest request) {
        return ResponseEntity.ok(payRollService.savePayroll(request));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<PayrollResponseDTO> getPayrollFull(@PathVariable Long id) {
        return ResponseEntity.ok(payRollService.getPayrollFullById(id));
    }

    @GetMapping("/all-full")
    public ResponseEntity<List<PayrollResponseDTO>> getAllPayrollFull() {
        return ResponseEntity.ok(payRollService.getAllPayrollFull());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        payRollService.deletePayroll(id);
        return ResponseEntity.ok("Payroll deleted successfully");
    }

    @GetMapping("/employee-wise")
    public ResponseEntity<List<EmployeePayrollViewDTO>> getEmployeeWise() {
        return ResponseEntity.ok(payRollService.getEmployeeWiseList());
    }

    @GetMapping("/employee-wise/{month}/{year}")
    public ResponseEntity<List<EmployeePayrollViewDTO>> getEmployeeWiseByMonthYear(
            @PathVariable int month, @PathVariable int year) {
        return ResponseEntity.ok(payRollService.getEmployeeWiseList(month, year));
    }

    // Generate payroll from attendance data
    @PostMapping("/generate-from-attendance")
    public ResponseEntity<PayrollResponseDTO> generateFromAttendance(@RequestBody Map<String, Object> body) {
        int month = (Integer) body.get("month");
        int year = (Integer) body.get("year");
        String addedBy = (String) body.get("addedBy");
        String location = body.get("location") != null ? body.get("location").toString() : "1";
        String employeeTypeFilter = body.get("employeeTypeFilter") != null
                ? body.get("employeeTypeFilter").toString()
                : null;
        return ResponseEntity
                .ok(payRollService.generatePayrollFromAttendance(month, year, addedBy, location, employeeTypeFilter));
    }

    // Generate payroll for a single employee from attendance data
    @PostMapping("/generate-for-employee")
    public ResponseEntity<PayrollResponseDTO> generateForEmployee(@RequestBody Map<String, Object> body) {
        int month = (Integer) body.get("month");
        int year = (Integer) body.get("year");
        String addedBy = (String) body.get("addedBy");
        String location = body.get("location") != null ? body.get("location").toString() : "1";
        Long employeeId = ((Number) body.get("employeeId")).longValue();
        return ResponseEntity
                .ok(payRollService.generatePayrollForEmployee(month, year, addedBy, location, employeeId));
    }

}
