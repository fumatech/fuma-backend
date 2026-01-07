package com.backend.ServiceImpl;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.DTO.AmountDTO;
import com.backend.DTO.EmployeePayrollDTO;
import com.backend.DTO.EmployeePayrollViewDTO;
import com.backend.DTO.PayrollEmployeeResponseDTO;
import com.backend.DTO.PayrollResponseDTO;
import com.backend.DTO.PayrollSaveRequest;
import com.backend.Entity.PayRoll;
import com.backend.Entity.PayrollDeduction;
import com.backend.Entity.PayrollEarning;
import com.backend.Entity.PayrollEmployee;
import com.backend.Repository.PayrollDeductionRepository;
import com.backend.Repository.PayrollEarningRepository;
import com.backend.Repository.PayrollEmployeeRepository;
import com.backend.Repository.PayrollRepository;
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

	@Override
	@Transactional
	public PayrollResponseDTO savePayroll(PayrollSaveRequest request) {

		// Parse Month / Year (01/2026)
		YearMonth ym = YearMonth.parse(request.getMonthYear(), java.time.format.DateTimeFormatter.ofPattern("MM/yyyy"));

		PayRoll payroll = new PayRoll();
		payroll.setPayrollName("Payroll for " + ym.getMonth() + " " + ym.getYear());
		payroll.setLocation(Long.parseLong(request.getLocation()));
		payroll.setMonth(ym.getMonthValue());
		payroll.setYear(ym.getYear());
		payroll.setStatus(request.getStatus());

		payroll = payrollRepository.save(payroll);

		// Save Employees
		for (EmployeePayrollDTO empDto : request.getEmployeePayrolls()) {

			PayrollEmployee emp = new PayrollEmployee();
			emp.setPayrollId(payroll.getId());
			emp.setEmployeeId(empDto.getEmployeeId());
			emp.setWorkDuration(empDto.getWorkDuration());
			emp.setUnit(empDto.getUnit());
			emp.setAmountPerUnit(empDto.getAmountPerUnit());
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

		return getPayrollFullById(payroll.getId()); // return full payroll with employees
	}

	@Override
	public PayrollResponseDTO getPayrollFullById(Long payrollId) {
		PayRoll payroll = payrollRepository.findById(payrollId)
				.orElseThrow(() -> new RuntimeException("Payroll not found"));

		PayrollResponseDTO response = new PayrollResponseDTO();
		response.setId(payroll.getId());
		response.setPayrollName(payroll.getPayrollName());
		response.setLocation(payroll.getLocation());
		response.setMonth(payroll.getMonth());
		response.setYear(payroll.getYear());
		response.setStatus(payroll.getStatus() != null ? payroll.getStatus().intValue() : null);

		List<PayrollEmployee> employees = payrollEmployeeRepository.findByPayrollId(payrollId);
		List<PayrollEmployeeResponseDTO> employeeDTOs = new ArrayList<>();

		for (PayrollEmployee emp : employees) {
			PayrollEmployeeResponseDTO empDto = new PayrollEmployeeResponseDTO();
			empDto.setEmployeeId(emp.getEmployeeId());
			empDto.setWorkDuration(emp.getWorkDuration());
			empDto.setUnit(emp.getUnit() != null ? emp.getUnit().toString() : null);
			empDto.setAmountPerUnit(emp.getAmountPerUnit());
			empDto.setTotal(emp.getTotal());
			empDto.setNote(emp.getNote());

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
		List<PayrollResponseDTO> allPayrolls = getAllPayrollFull(); // get payrolls with employees
		List<EmployeePayrollViewDTO> employeeWiseList = new ArrayList<>();

		for (PayrollResponseDTO payroll : allPayrolls) {
			for (PayrollEmployeeResponseDTO emp : payroll.getEmployees()) {
				EmployeePayrollViewDTO view = new EmployeePayrollViewDTO();
				view.setEmployeeId(emp.getEmployeeId());
				view.setPayrollName(payroll.getPayrollName());
				view.setPayrollId(payroll.getId());
				view.setMonth(payroll.getMonth());
				view.setYear(payroll.getYear());
				view.setTotal(emp.getTotal());
				view.setNote(emp.getNote());
				view.setEarnings(emp.getEarnings());
				view.setDeductions(emp.getDeductions());

				employeeWiseList.add(view); // add every employee-payroll entry
			}
		}

		return employeeWiseList;
	}
}
