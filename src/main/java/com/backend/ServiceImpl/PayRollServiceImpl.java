package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.DTO.AmountDTO;
import com.backend.DTO.BulkPayrollTransactionRequest;
import com.backend.DTO.EmployeePayrollDTO;
import com.backend.DTO.EmployeePayrollViewDTO;
import com.backend.DTO.PayrollEmployeeResponseDTO;
import com.backend.DTO.PayrollResponseDTO;
import com.backend.DTO.PayrollSalaryTransactionDTO;
import com.backend.DTO.PayrollSaveRequest;
import com.backend.Entity.PayRoll;
import com.backend.Entity.PaymentAccount;
import com.backend.Entity.PayrollDeduction;
import com.backend.Entity.PayrollEarning;
import com.backend.Entity.PayrollEmployee;
import com.backend.Entity.Transaction;
import com.backend.Repository.PaymentAccountRepo;
import com.backend.Repository.PayrollDeductionRepository;
import com.backend.Repository.PayrollEarningRepository;
import com.backend.Repository.PayrollEmployeeRepository;
import com.backend.Repository.PayrollRepository;
import com.backend.Repository.TransactionRepo;
import com.backend.Service.AttendanceService;
import com.backend.Service.PayRollService;

@Service
public class PayRollServiceImpl implements PayRollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollEmployeeRepository payrollEmployeeRepository;

    @Autowired
    private PayrollEarningRepository payrollEarningRepository;

    @Autowired
    private PayrollDeductionRepository payrollDeductionRepository;

    @Autowired
    private PaymentAccountRepo paymentAccountRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AttendanceService attendanceService;

    @Transactional
    public List<Transaction> createBulkPayrollEmployeeTransactions(List<BulkPayrollTransactionRequest> requests) {

        List<Transaction> savedTransactions = new ArrayList<>();

        for (BulkPayrollTransactionRequest req : requests) {

            PaymentAccount account = paymentAccountRepo.findById(req.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            PayrollEmployee payrollEmployee = payrollEmployeeRepository.findById(req.getPayrollEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Payroll employee not found"));

            if (account.getBalance().compareTo(req.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance in account " + account.getAccountName());
            }

            // Deduct balance
            account.setBalance(account.getBalance().subtract(req.getAmount()));
            paymentAccountRepo.save(account);

            Transaction tx = new Transaction();
            tx.setPayrollEmployee(payrollEmployee);
            tx.setPaymentAccount(account);
            tx.setPaymentMethod(req.getPaymentMethod());
            tx.setAmount(req.getAmount());
            tx.setTransactionType("salary");
            tx.setAddedBy(req.getAddedBy());
            tx.setNote(req.getNote());
            tx.setDate(req.getDate() != null ? req.getDate() : LocalDateTime.now());
            tx.setBalance(account.getBalance());

            savedTransactions.add(transactionRepo.save(tx));
        }

        return savedTransactions;
    }

    @Transactional
    public List<Transaction> createBulkPayrollEmployeeTransactions(Long accountId,
            List<BulkPayrollTransactionRequest> requests) {

        PaymentAccount account = paymentAccountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal runningBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

        List<Transaction> savedTransactions = new ArrayList<>();

        for (BulkPayrollTransactionRequest req : requests) {

            PayrollEmployee payrollEmployee = payrollEmployeeRepository.findById(req.getPayrollEmployeeId())
                    .orElseThrow(
                            () -> new RuntimeException("Payroll employee not found: " + req.getPayrollEmployeeId()));

            if (runningBalance.compareTo(req.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            runningBalance = runningBalance.subtract(req.getAmount());

            Transaction tx = new Transaction();
            tx.setPayrollEmployee(payrollEmployee);
            tx.setPaymentAccount(account);

            //  Map ALL fields
            tx.setPaymentMethod(req.getPaymentMethod());
            tx.setAmount(req.getAmount());
            tx.setTransactionType("salary"); // force salary
            tx.setAddedBy(req.getAddedBy());
            tx.setNote(req.getNote());
            tx.setDate(req.getDate() != null ? req.getDate() : LocalDateTime.now());
            tx.setBalance(runningBalance);

            savedTransactions.add(transactionRepo.save(tx));
        }

        account.setBalance(runningBalance);
        paymentAccountRepo.save(account);

        return savedTransactions;
    }

    @Override
    @Transactional
    public PayrollResponseDTO savePayroll(PayrollSaveRequest request) {

        // Parse Month / Year (01/2026)
        YearMonth ym = YearMonth.parse(request.getMonthYear(), java.time.format.DateTimeFormatter.ofPattern("MM/yyyy"));

        PayRoll payroll;

        // ==========================
        // EDIT MODE
        // ==========================
        if (request.getPayrollId() != null) {

            payroll = payrollRepository.findById(request.getPayrollId())
                    .orElseThrow(() -> new RuntimeException("Payroll not found"));

            payroll.setLocation(Long.parseLong(request.getLocation()));
            payroll.setMonth(ym.getMonthValue());
            payroll.setYear(ym.getYear());
            payroll.setAddedBy(request.getAddedBy());
            payroll.setStatus(request.getStatus());
            payroll.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt()
                    : new java.sql.Date(System.currentTimeMillis()));

            //  Delete old employees data
            List<PayrollEmployee> oldEmployees = payrollEmployeeRepository.findByPayrollId(payroll.getId());

            for (PayrollEmployee emp : oldEmployees) {
                payrollEarningRepository.deleteByPayrollEmployeeId(emp.getId());
                payrollDeductionRepository.deleteByPayrollEmployeeId(emp.getId());
            }

            payrollEmployeeRepository.deleteByPayrollId(payroll.getId());
        } // ==========================
        //  CREATE MODE
        // ==========================
        else {
            payroll = new PayRoll();
            payroll.setPayrollName("Payroll for " + ym.getMonth() + " " + ym.getYear());
            payroll.setLocation(Long.parseLong(request.getLocation()));
            payroll.setMonth(ym.getMonthValue());
            payroll.setYear(ym.getYear());
            payroll.setAddedBy(request.getAddedBy());
            payroll.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt()
                    : new java.sql.Date(System.currentTimeMillis()));
            payroll.setStatus(request.getStatus());
        }

        payroll = payrollRepository.save(payroll);

        // ==========================
        // SAVE EMPLOYEES AGAIN
        // ==========================
        for (EmployeePayrollDTO empDto : request.getEmployeePayrolls()) {

            PayrollEmployee emp = new PayrollEmployee();
            emp.setPayrollId(payroll.getId());
            emp.setEmployeeId(empDto.getEmployeeId());
            emp.setWorkDuration(empDto.getWorkDuration());
            emp.setUnit(empDto.getUnit());
            emp.setAmountPerUnit(empDto.getAmountPerUnit());
            emp.setBasic(empDto.getBasic());
            emp.setTotal(empDto.getTotal());
            emp.setNote(empDto.getNote());

            emp = payrollEmployeeRepository.save(emp);

            // Earnings
            if (empDto.getEarnings() != null) {
                for (AmountDTO e : empDto.getEarnings()) {
                    PayrollEarning earning = new PayrollEarning();
                    earning.setPayrollEmployeeId(emp.getId());
                    earning.setDescription(e.getDescription());
                    earning.setAmountType(e.getAmountType());
                    earning.setAmount(e.getAmount());
                    payrollEarningRepository.save(earning);
                }
            }

            // Deductions
            if (empDto.getDeductions() != null) {
                for (AmountDTO d : empDto.getDeductions()) {
                    PayrollDeduction deduction = new PayrollDeduction();
                    deduction.setPayrollEmployeeId(emp.getId());
                    deduction.setDescription(d.getDescription());
                    deduction.setAmountType(d.getAmountType());
                    deduction.setAmount(d.getAmount());
                    payrollDeductionRepository.save(deduction);
                }
            }
        }

        return getPayrollFullById(payroll.getId());
    }

    @Override
    public PayrollResponseDTO getPayrollFullById(Long payrollId) {

        PayRoll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        PayrollResponseDTO response = new PayrollResponseDTO();
        response.setId(payroll.getId());
        response.setPayrollName(payroll.getPayrollName());
        response.setLocation(payroll.getLocation());
        response.setAddedBy(payroll.getAddedBy());
        response.setCreatedAt(payroll.getCreatedAt());
        response.setMonth(payroll.getMonth());
        response.setYear(payroll.getYear());
        response.setStatus(payroll.getStatus() != null ? payroll.getStatus().intValue() : null);

        List<PayrollEmployee> employees = payrollEmployeeRepository.findByPayrollId(payrollId);
        List<PayrollEmployeeResponseDTO> employeeDTOs = new ArrayList<>();

        for (PayrollEmployee emp : employees) {

            PayrollEmployeeResponseDTO empDto = new PayrollEmployeeResponseDTO();
            empDto.setPayrollEmployeeId(emp.getId());
            empDto.setEmployeeId(emp.getEmployeeId());
            empDto.setWorkDuration(emp.getWorkDuration());
            empDto.setUnit(emp.getUnit() != null ? emp.getUnit().toString() : null);
            empDto.setAmountPerUnit(emp.getAmountPerUnit());
            empDto.setBasic(emp.getBasic());
            empDto.setTotal(emp.getTotal());
            empDto.setNote(emp.getNote());

            // TRANSACTIONS (PER EMPLOYEE)
            List<PayrollSalaryTransactionDTO> txDtos = new ArrayList<>();

            transactionRepo.findByPayrollEmployee_Id(emp.getId()).forEach(tx -> {
                PayrollSalaryTransactionDTO t = new PayrollSalaryTransactionDTO();
                t.setTransactionId(tx.getId());
                t.setPayrollEmployeeId(emp.getId());
                t.setAccountId(tx.getPaymentAccount().getId());
                t.setPaymentMethod(tx.getPaymentMethod());
                t.setAmount(tx.getAmount());
                t.setNote(tx.getNote());
                t.setDate(tx.getDate());
                t.setBalance(tx.getBalance());
                txDtos.add(t);
            });

            empDto.setTransactions(txDtos);

            // Earnings
            List<AmountDTO> earningsDTOs = new ArrayList<>();
            payrollEarningRepository.findByPayrollEmployeeId(emp.getId()).forEach(e -> {
                AmountDTO a = new AmountDTO();
                a.setDescription(e.getDescription());
                a.setAmountType(e.getAmountType());
                a.setAmount(e.getAmount());
                earningsDTOs.add(a);
            });
            empDto.setEarnings(earningsDTOs);

            // Deductions
            List<AmountDTO> deductionsDTOs = new ArrayList<>();
            payrollDeductionRepository.findByPayrollEmployeeId(emp.getId()).forEach(d -> {
                AmountDTO a = new AmountDTO();
                a.setDescription(d.getDescription());
                a.setAmountType(d.getAmountType());
                a.setAmount(d.getAmount());
                deductionsDTOs.add(a);
            });
            empDto.setDeductions(deductionsDTOs);

            employeeDTOs.add(empDto);
        }

        response.setEmployees(employeeDTOs);
        return response;
    }

    @Override
    public List<PayrollResponseDTO> getAllPayrollFull() {
        List<PayRoll> payrolls = payrollRepository.findAll();
        List<PayrollResponseDTO> responseList = new ArrayList<>();
        for (PayRoll payroll : payrolls) {
            responseList.add(getPayrollFullById(payroll.getId()));
        }
        return responseList;
    }

    @Override
    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }

    @Override
    public List<EmployeePayrollViewDTO> getEmployeeWiseList() {
        return buildEmployeeWiseList(payrollRepository.findAll());
    }

    @Override
    public List<EmployeePayrollViewDTO> getEmployeeWiseList(int month, int year) {
        return buildEmployeeWiseList(payrollRepository.findByMonthAndYear(month, year));
    }

    private List<EmployeePayrollViewDTO> buildEmployeeWiseList(List<PayRoll> payrolls) {
        List<EmployeePayrollViewDTO> employeeWiseList = new ArrayList<>();

        if (payrolls.isEmpty()) {
            return employeeWiseList;
        }

        // Batch fetch all employees for all payrolls (1 query instead of N)
        List<Long> payrollIds = payrolls.stream().map(PayRoll::getId).collect(java.util.stream.Collectors.toList());
        List<PayrollEmployee> allEmployees = payrollEmployeeRepository.findByPayrollIdIn(payrollIds);

        if (allEmployees.isEmpty()) {
            return employeeWiseList;
        }

        // Build payroll lookup map
        Map<Long, PayRoll> payrollMap = new java.util.HashMap<>();
        for (PayRoll p : payrolls) {
            payrollMap.put(p.getId(), p);
        }

        // Batch fetch all earnings, deductions, transactions (3 queries instead of 3*M)
        List<Long> empIds = allEmployees.stream().map(PayrollEmployee::getId).collect(java.util.stream.Collectors.toList());
        List<PayrollEarning> allEarnings = payrollEarningRepository.findByPayrollEmployeeIdIn(empIds);
        List<PayrollDeduction> allDeductions = payrollDeductionRepository.findByPayrollEmployeeIdIn(empIds);
        List<Transaction> allTransactions = transactionRepo.findByPayrollEmployee_IdIn(empIds);

        // Group by payrollEmployeeId
        Map<Long, List<PayrollEarning>> earningsMap = new java.util.HashMap<>();
        for (PayrollEarning e : allEarnings) {
            earningsMap.computeIfAbsent(e.getPayrollEmployeeId(), k -> new ArrayList<>()).add(e);
        }
        Map<Long, List<PayrollDeduction>> deductionsMap = new java.util.HashMap<>();
        for (PayrollDeduction d : allDeductions) {
            deductionsMap.computeIfAbsent(d.getPayrollEmployeeId(), k -> new ArrayList<>()).add(d);
        }
        Map<Long, List<Transaction>> transactionsMap = new java.util.HashMap<>();
        for (Transaction tx : allTransactions) {
            Long peId = tx.getPayrollEmployee().getId();
            transactionsMap.computeIfAbsent(peId, k -> new ArrayList<>()).add(tx);
        }

        // Build DTOs from pre-fetched data (0 additional queries)
        for (PayrollEmployee emp : allEmployees) {
            PayRoll payroll = payrollMap.get(emp.getPayrollId());
            if (payroll == null) {
                continue;
            }

            EmployeePayrollViewDTO view = new EmployeePayrollViewDTO();
            view.setPayrollEmployeeId(emp.getId());
            view.setEmployeeId(emp.getEmployeeId());
            view.setPayrollName(payroll.getPayrollName());
            view.setPayrollId(payroll.getId());
            view.setMonth(payroll.getMonth());
            view.setYear(payroll.getYear());
            view.setAddedBy(payroll.getAddedBy());
            view.setCreatedAt(payroll.getCreatedAt());
            view.setTotal(emp.getTotal());
            view.setNote(emp.getNote());
            view.setBasic(emp.getBasic());

            view.setEarnings(earningsMap.getOrDefault(emp.getId(), java.util.Collections.emptyList()).stream().map(e -> {
                AmountDTO a = new AmountDTO();
                a.setDescription(e.getDescription());
                a.setAmountType(e.getAmountType());
                a.setAmount(e.getAmount());
                return a;
            }).collect(java.util.stream.Collectors.toList()));

            view.setDeductions(deductionsMap.getOrDefault(emp.getId(), java.util.Collections.emptyList()).stream().map(d -> {
                AmountDTO a = new AmountDTO();
                a.setDescription(d.getDescription());
                a.setAmountType(d.getAmountType());
                a.setAmount(d.getAmount());
                return a;
            }).collect(java.util.stream.Collectors.toList()));

            List<PayrollSalaryTransactionDTO> txDtos = new ArrayList<>();
            for (Transaction tx : transactionsMap.getOrDefault(emp.getId(), java.util.Collections.emptyList())) {
                PayrollSalaryTransactionDTO t = new PayrollSalaryTransactionDTO();
                t.setTransactionId(tx.getId());
                t.setPayrollEmployeeId(emp.getId());
                t.setAccountId(tx.getPaymentAccount().getId());
                t.setPaymentMethod(tx.getPaymentMethod());
                t.setAmount(tx.getAmount());
                t.setNote(tx.getNote());
                t.setDate(tx.getDate());
                t.setBalance(tx.getBalance());
                txDtos.add(t);
            }
            view.setTransactions(txDtos);

            employeeWiseList.add(view);
        }

        return employeeWiseList;
    }

    @Override
    @Transactional
    public PayrollResponseDTO generatePayrollFromAttendance(int month, int year, String addedBy, String location) {
        return generatePayrollFromAttendance(month, year, addedBy, location, null);
    }

    @Override
    @Transactional
    public PayrollResponseDTO generatePayrollFromAttendance(int month, int year, String addedBy, String location,
            String employeeTypeFilter) {

        // Get all employees' monthly working summary
        List<Map<String, Object>> summaries = attendanceService.getAllEmployeesMonthlyWorkingSummary(month, year);

        if (summaries.isEmpty()) {
            throw new RuntimeException("No attendance data found for " + month + "/" + year);
        }

        // Filter by employee type if specified
        if (employeeTypeFilter != null && !employeeTypeFilter.isEmpty()) {
            summaries = summaries.stream().filter(s -> {
                String empType = (String) s.get("employeeType");
                if ("FULL_TIME".equals(employeeTypeFilter)) {
                    return empType == null || "FULL_TIME".equals(empType);
                } else if ("HOURLY_FREELANCER".equals(employeeTypeFilter)) {
                    return "HOURLY".equals(empType) || "FREELANCER".equals(empType);
                }
                return true;
            }).collect(java.util.stream.Collectors.toList());

            if (summaries.isEmpty()) {
                throw new RuntimeException("No " + employeeTypeFilter + " employees found for " + month + "/" + year);
            }
        }

        // Build PayrollSaveRequest from attendance data
        String monthYear = String.format("%02d/%d", month, year);

        List<EmployeePayrollDTO> employeePayrolls = new ArrayList<>();

        for (Map<String, Object> summary : summaries) {
            EmployeePayrollDTO empDto = new EmployeePayrollDTO();

            Long employeeId = (Long) summary.get("employeeId");
            String employeeType = (String) summary.get("employeeType");
            Double totalWorkingHours = ((Number) summary.get("totalWorkingHours")).doubleValue();
            Long presentDays = (Long) summary.get("presentDays");

            empDto.setEmployeeId(employeeId);
            empDto.setWorkDuration(totalWorkingHours);

            // Determine if employee is hourly/freelancer: check type first, fallback to hourlyRate
            Object hourlyRateObj = summary.get("hourlyRate");
            Double hourlyRate = hourlyRateObj != null ? ((Number) hourlyRateObj).doubleValue() : 0.0;
            boolean isHourly = "HOURLY".equals(employeeType) || "FREELANCER".equals(employeeType)
                    || (employeeType == null && hourlyRate > 0);

            if (isHourly) {
                String effectiveType = employeeType != null ? employeeType : "HOURLY";
                empDto.setUnit(totalWorkingHours);
                empDto.setAmountPerUnit(hourlyRate);
                empDto.setBasic(totalWorkingHours * hourlyRate);
                empDto.setTotal(totalWorkingHours * hourlyRate);
                empDto.setNote("Auto-generated from attendance. Type: " + effectiveType
                        + ". Hours: " + totalWorkingHours + " @ " + hourlyRate + "/hr");
            } else {
                // Full-time: basic salary
                Object basicSalaryObj = summary.get("basicSalary");
                Double basicSalary = basicSalaryObj != null ? ((Number) basicSalaryObj).doubleValue() : 0.0;
                empDto.setUnit(1.0);
                empDto.setAmountPerUnit(basicSalary);
                empDto.setBasic(basicSalary);
                empDto.setTotal(basicSalary);
                empDto.setNote("Auto-generated from attendance. Type: FULL_TIME. Days present: " + presentDays);
            }

            empDto.setEarnings(new ArrayList<>());
            empDto.setDeductions(new ArrayList<>());
            employeePayrolls.add(empDto);
        }

        PayrollSaveRequest request = new PayrollSaveRequest();
        request.setLocation(location != null ? location : "1");
        request.setMonthYear(monthYear);
        request.setAddedBy(addedBy);
        request.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        request.setStatus(0L); // Draft
        request.setEmployeePayrolls(employeePayrolls);

        return savePayroll(request);
    }

    @Override
    @Transactional
    public PayrollResponseDTO generatePayrollForEmployee(int month, int year, String addedBy, String location,
            Long employeeId) {

        Map<String, Object> summary = attendanceService.getMonthlyWorkingSummary(employeeId, month, year);

        if (summary == null || summary.isEmpty()) {
            throw new RuntimeException("No attendance data found for employee " + employeeId + " in " + month + "/" + year);
        }

        String monthYear = String.format("%02d/%d", month, year);

        EmployeePayrollDTO empDto = new EmployeePayrollDTO();
        String employeeType = (String) summary.get("employeeType");
        Double totalWorkingHours = ((Number) summary.get("totalWorkingHours")).doubleValue();
        Long presentDays = (Long) summary.get("presentDays");

        empDto.setEmployeeId(employeeId);
        empDto.setWorkDuration(totalWorkingHours);

        // Determine if employee is hourly/freelancer: check type first, fallback to hourlyRate
        Object hourlyRateObj = summary.get("hourlyRate");
        Double hourlyRate = hourlyRateObj != null ? ((Number) hourlyRateObj).doubleValue() : 0.0;
        boolean isHourly = "HOURLY".equals(employeeType) || "FREELANCER".equals(employeeType)
                || (employeeType == null && hourlyRate > 0);

        if (isHourly) {
            String effectiveType = employeeType != null ? employeeType : "HOURLY";
            empDto.setUnit(totalWorkingHours);
            empDto.setAmountPerUnit(hourlyRate);
            empDto.setBasic(totalWorkingHours * hourlyRate);
            empDto.setTotal(totalWorkingHours * hourlyRate);
            empDto.setNote("Auto-generated from attendance. Type: " + effectiveType
                    + ". Hours: " + totalWorkingHours + " @ " + hourlyRate + "/hr");
        } else {
            Object basicSalaryObj = summary.get("basicSalary");
            Double basicSalary = basicSalaryObj != null ? ((Number) basicSalaryObj).doubleValue() : 0.0;
            empDto.setUnit(1.0);
            empDto.setAmountPerUnit(basicSalary);
            empDto.setBasic(basicSalary);
            empDto.setTotal(basicSalary);
            empDto.setNote("Auto-generated from attendance. Type: FULL_TIME. Days present: " + presentDays);
        }

        empDto.setEarnings(new ArrayList<>());
        empDto.setDeductions(new ArrayList<>());

        PayrollSaveRequest request = new PayrollSaveRequest();
        request.setLocation(location != null ? location : "1");
        request.setMonthYear(monthYear);
        request.setAddedBy(addedBy);
        request.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        request.setStatus(0L);

        // Check for existing payroll for this month/year — update instead of creating duplicate
        List<PayRoll> existingPayrolls = payrollRepository.findByMonthAndYear(month, year);
        if (!existingPayrolls.isEmpty()) {
            // Use the latest existing payroll
            PayRoll existing = existingPayrolls.get(existingPayrolls.size() - 1);

            // Check if this employee already has a record in this payroll
            List<PayrollEmployee> existingEmployees = payrollEmployeeRepository.findByPayrollId(existing.getId());
            boolean employeeExists = existingEmployees.stream()
                    .anyMatch(e -> e.getEmployeeId().equals(employeeId));

            if (employeeExists) {
                // Update mode — reuse existing payroll
                request.setPayrollId(existing.getId());

                // Keep other employees, replace this one
                List<EmployeePayrollDTO> allEmpDtos = new ArrayList<>();
                for (PayrollEmployee existingEmp : existingEmployees) {
                    if (existingEmp.getEmployeeId().equals(employeeId)) {
                        allEmpDtos.add(empDto);
                    } else {
                        // Preserve existing employee data
                        EmployeePayrollDTO existingDto = new EmployeePayrollDTO();
                        existingDto.setEmployeeId(existingEmp.getEmployeeId());
                        existingDto.setWorkDuration(existingEmp.getWorkDuration());
                        existingDto.setUnit(existingEmp.getUnit());
                        existingDto.setAmountPerUnit(existingEmp.getAmountPerUnit());
                        existingDto.setBasic(existingEmp.getBasic());
                        existingDto.setTotal(existingEmp.getTotal());
                        existingDto.setNote(existingEmp.getNote());

                        List<AmountDTO> earnings = new ArrayList<>();
                        for (PayrollEarning pe : payrollEarningRepository.findByPayrollEmployeeId(existingEmp.getId())) {
                            AmountDTO a = new AmountDTO();
                            a.setDescription(pe.getDescription());
                            a.setAmountType(pe.getAmountType());
                            a.setAmount(pe.getAmount());
                            earnings.add(a);
                        }
                        existingDto.setEarnings(earnings);

                        List<AmountDTO> deductions = new ArrayList<>();
                        for (PayrollDeduction pd : payrollDeductionRepository.findByPayrollEmployeeId(existingEmp.getId())) {
                            AmountDTO a = new AmountDTO();
                            a.setDescription(pd.getDescription());
                            a.setAmountType(pd.getAmountType());
                            a.setAmount(pd.getAmount());
                            deductions.add(a);
                        }
                        existingDto.setDeductions(deductions);

                        allEmpDtos.add(existingDto);
                    }
                }
                request.setEmployeePayrolls(allEmpDtos);
            } else {
                // Employee not in existing payroll — add to it
                request.setPayrollId(existing.getId());

                List<EmployeePayrollDTO> allEmpDtos = new ArrayList<>();
                for (PayrollEmployee existingEmp : existingEmployees) {
                    EmployeePayrollDTO existingDto = new EmployeePayrollDTO();
                    existingDto.setEmployeeId(existingEmp.getEmployeeId());
                    existingDto.setWorkDuration(existingEmp.getWorkDuration());
                    existingDto.setUnit(existingEmp.getUnit());
                    existingDto.setAmountPerUnit(existingEmp.getAmountPerUnit());
                    existingDto.setBasic(existingEmp.getBasic());
                    existingDto.setTotal(existingEmp.getTotal());
                    existingDto.setNote(existingEmp.getNote());

                    List<AmountDTO> earnings = new ArrayList<>();
                    for (PayrollEarning pe : payrollEarningRepository.findByPayrollEmployeeId(existingEmp.getId())) {
                        AmountDTO a = new AmountDTO();
                        a.setDescription(pe.getDescription());
                        a.setAmountType(pe.getAmountType());
                        a.setAmount(pe.getAmount());
                        earnings.add(a);
                    }
                    existingDto.setEarnings(earnings);

                    List<AmountDTO> deductions = new ArrayList<>();
                    for (PayrollDeduction pd : payrollDeductionRepository.findByPayrollEmployeeId(existingEmp.getId())) {
                        AmountDTO a = new AmountDTO();
                        a.setDescription(pd.getDescription());
                        a.setAmountType(pd.getAmountType());
                        a.setAmount(pd.getAmount());
                        deductions.add(a);
                    }
                    existingDto.setDeductions(deductions);

                    allEmpDtos.add(existingDto);
                }
                allEmpDtos.add(empDto);
                request.setEmployeePayrolls(allEmpDtos);
            }
        } else {
            request.setEmployeePayrolls(List.of(empDto));
        }

        return savePayroll(request);
    }

}
