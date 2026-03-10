package com.backend.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Holiday;
import com.backend.Repository.AddLeaveRepo;
import com.backend.Repository.AttendanceRepo;
import com.backend.Repository.DepartmentRepo;
import com.backend.Repository.DesignationRepo;
import com.backend.Repository.HolidayRepo;
import com.backend.Repository.PayrollEmployeeRepository;
import com.backend.Repository.UserRepo;
import com.backend.Entity.Department;

@RestController
@RequestMapping("/hrm")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class HRMDashboardController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private AddLeaveRepo addLeaveRepo;

    @Autowired
    private PayrollEmployeeRepository payrollEmployeeRepository;

    @Autowired
    private HolidayRepo holidayRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DesignationRepo designationRepo;

    @GetMapping("/dashboard-summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        // --- Employee Stats (2 queries) ---
        long totalEmployees = userRepo.count();
        long activeEmployees = userRepo.countByIsActive(true);
        summary.put("totalEmployees", totalEmployees);
        summary.put("activeEmployees", activeEmployees);

        // --- Attendance: present today (1 query) ---
        LocalDate today = LocalDate.now();
        long presentToday = attendanceRepo.countPresentByDate(today);
        summary.put("presentToday", presentToday);

        // --- Attendance Trend: last 7 days (1 query) ---
        LocalDate sevenDaysAgo = today.minusDays(6);
        List<Object[]> trendRaw = attendanceRepo.countPresentGroupByDateBetween(sevenDaysAgo, today);
        Map<LocalDate, Long> trendMap = new HashMap<>();
        for (Object[] row : trendRaw) {
            trendMap.put((LocalDate) row[0], (Long) row[1]);
        }
        List<Map<String, Object>> attendanceTrend = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.toString());
            dayData.put("day", date.getDayOfWeek().toString().substring(0, 3));
            long present = trendMap.getOrDefault(date, 0L);
            dayData.put("present", present);
            dayData.put("absent", totalEmployees - present);
            attendanceTrend.add(dayData);
        }
        summary.put("attendanceTrend", attendanceTrend);

        // --- Leave Stats (2 queries) ---
        long onLeave = addLeaveRepo.countCurrentlyOnLeave();
        long pendingLeaves = addLeaveRepo.countPending();
        summary.put("onLeave", onLeave);
        summary.put("pendingLeaves", pendingLeaves);

        // Leave status breakdown (1 query)
        List<Object[]> leaveStatusRaw = addLeaveRepo.countByStatusGroup();
        long approvedLeaves = 0, rejectedLeaves = 0;
        for (Object[] row : leaveStatusRaw) {
            long status = (Long) row[0];
            long count = (Long) row[1];
            if (status == 1) {
                approvedLeaves = count; 
            }else if (status == 2) {
                rejectedLeaves = count;
            }
        }
        List<Map<String, Object>> leaveStatusData = new ArrayList<>();
        Map<String, Object> approved = new HashMap<>();
        approved.put("name", "Approved");
        approved.put("value", approvedLeaves);
        leaveStatusData.add(approved);
        Map<String, Object> pending = new HashMap<>();
        pending.put("name", "Pending");
        pending.put("value", pendingLeaves);
        leaveStatusData.add(pending);
        Map<String, Object> rejected = new HashMap<>();
        rejected.put("name", "Rejected");
        rejected.put("value", rejectedLeaves);
        leaveStatusData.add(rejected);
        summary.put("leaveStatusData", leaveStatusData);

        // --- Department & Designation counts (2 queries) ---
        long totalDepartments = departmentRepo.count();
        long totalDesignations = designationRepo.count();
        summary.put("totalDepartments", totalDepartments);
        summary.put("totalDesignations", totalDesignations);

        // Department distribution (2 queries: one for dept names, one for user counts)
        List<Object[]> deptCountRaw = userRepo.countByDepartmentGroup();
        List<Department> allDepts = departmentRepo.findAll();
        Map<Long, String> deptNameMap = new HashMap<>();
        for (Department d : allDepts) {
            deptNameMap.put(d.getId(), d.getDepartment());
        }
        List<Map<String, Object>> departmentData = new ArrayList<>();
        for (Object[] row : deptCountRaw) {
            Long deptId = (Long) row[0];
            Long count = (Long) row[1];
            Map<String, Object> dept = new HashMap<>();
            dept.put("name", deptNameMap.getOrDefault(deptId, "Unassigned"));
            dept.put("value", count);
            departmentData.add(dept);
        }
        summary.put("departmentData", departmentData);

        // --- Payroll Stats ---
        summary.put("totalPayrolls", payrollEmployeeRepository.countAll());
        summary.put("totalPayrollAmount", payrollEmployeeRepository.sumTotal());

        // Monthly payroll data (1 query)
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        List<Object[]> monthlyRaw = payrollEmployeeRepository.sumTotalGroupByMonth();
        Map<Integer, Double> monthlyMap = new HashMap<>();
        for (Object[] row : monthlyRaw) {
            monthlyMap.put((Integer) row[0], ((Number) row[1]).doubleValue());
        }
        List<Map<String, Object>> monthlyPayrollData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("month", months[i]);
            m.put("amount", monthlyMap.getOrDefault(i + 1, 0.0));
            monthlyPayrollData.add(m);
        }
        summary.put("monthlyPayrollData", monthlyPayrollData);

        // --- Holidays (1 query) ---
        Date todaySql = Date.valueOf(today);
        long upcomingHolidayCount = holidayRepo.countByStartDateGreaterThanEqual(todaySql);
        summary.put("upcomingHolidays", upcomingHolidayCount);

        List<Holiday> upcomingHolidayList = holidayRepo.findByStartDateGreaterThanEqualOrderByStartDateAsc(todaySql);
        List<Map<String, Object>> holidayData = new ArrayList<>();
        int limit = Math.min(upcomingHolidayList.size(), 5);
        for (int i = 0; i < limit; i++) {
            Holiday h = upcomingHolidayList.get(i);
            Map<String, Object> hm = new HashMap<>();
            hm.put("id", h.getId());
            hm.put("name", h.getName());
            hm.put("startDate", h.getStartDate());
            hm.put("endDate", h.getEndDate());
            holidayData.add(hm);
        }
        summary.put("upcomingHolidayList", holidayData);

        return ResponseEntity.ok(summary);
    }
}
