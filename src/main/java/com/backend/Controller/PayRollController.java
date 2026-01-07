package com.backend.Controller;

import java.util.List;

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

import com.backend.DTO.EmployeePayrollViewDTO;
import com.backend.DTO.PayrollResponseDTO;
import com.backend.DTO.PayrollSaveRequest;
import com.backend.Service.PayRollService;

@RestController
@RequestMapping("/payroll")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PayRollController {

	@Autowired
	private PayRollService payRollService;

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

}
