package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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

	@Transactional
	public List<Transaction> createBulkPayrollEmployeeTransactions(List<BulkPayrollTransactionRequest> requests) {

		List<Transaction> savedTransactions = new ArrayList<>();

		for (BulkPayrollTransactionRequest req : requests) {

			PaymentAccount account = paymentAccountRepo.findById(req.getAccountId())
					.orElseThrow(() -> new RuntimeException("Account not found"));

			PayrollEmployee payrollEmployee = payrollEmployeeRepository.findById(req.getPayrollEmployeeId())
					.orElseThrow(() -> new RuntimeException("Payroll employee not found"));
//
//			if (account.getBalance().compareTo(req.getAmount()) < 0) {
//				throw new RuntimeException("Insufficient balance for account " + account.getId());
//			}

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

			// 🔥 Map ALL fields
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
		// ✅ EDIT MODE
		// ==========================
		if (request.getPayrollId() != null) {

			payroll = payrollRepository.findById(request.getPayrollId())
					.orElseThrow(() -> new RuntimeException("Payroll not found"));

			payroll.setLocation(Long.parseLong(request.getLocation()));
			payroll.setMonth(ym.getMonthValue());
			payroll.setYear(ym.getYear());
			payroll.setAddedBy(request.getAddedBy());
			payroll.setStatus(request.getStatus());

			// ❗ Do NOT change createdAt during edit

			// 🔥 Delete old employees data
			List<PayrollEmployee> oldEmployees = payrollEmployeeRepository.findByPayrollId(payroll.getId());

			for (PayrollEmployee emp : oldEmployees) {
				payrollEarningRepository.deleteByPayrollEmployeeId(emp.getId());
				payrollDeductionRepository.deleteByPayrollEmployeeId(emp.getId());
			}

			payrollEmployeeRepository.deleteByPayrollId(payroll.getId());
		}
		// ==========================
		// ✅ CREATE MODE
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
			empDto.setEmployeeId(emp.getEmployeeId());
			empDto.setWorkDuration(emp.getWorkDuration());
			empDto.setUnit(emp.getUnit() != null ? emp.getUnit().toString() : null);
			empDto.setAmountPerUnit(emp.getAmountPerUnit());
			empDto.setBasic(emp.getBasic());
			empDto.setTotal(emp.getTotal());
			empDto.setNote(emp.getNote());

			// ✅ TRANSACTIONS (PER EMPLOYEE)
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

		List<PayrollResponseDTO> allPayrolls = getAllPayrollFull();
		List<EmployeePayrollViewDTO> employeeWiseList = new ArrayList<>();

		for (PayrollResponseDTO payroll : allPayrolls) {

			for (PayrollEmployeeResponseDTO emp : payroll.getEmployees()) {

				EmployeePayrollViewDTO view = new EmployeePayrollViewDTO();
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

				// existing
				view.setEarnings(emp.getEarnings());
				view.setDeductions(emp.getDeductions());

				// ✅ ADD THIS
				view.setTransactions(emp.getTransactions());

				employeeWiseList.add(view);
			}
		}

		return employeeWiseList;
	}

}
