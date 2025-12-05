package com.backend.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.backend.Entity.PaymentMethod;
import com.backend.Service.PaymentMethodService;

@RestController
@RequestMapping("/payment-method")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com"   },
allowCredentials = "true"
)
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService paymentMethodService;

	@PostMapping("/save")
	public ResponseEntity<PaymentMethod> savePermission(@RequestBody PaymentMethod paymentMethod) {
		PaymentMethod savedPay = paymentMethodService.savePaymentMethod(paymentMethod);
		return new ResponseEntity<>(savedPay, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<PaymentMethod>> getAllPermissions() {
		List<PaymentMethod> payment = paymentMethodService.getAllPaymentMethod();
		return new ResponseEntity<>(payment, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentMethod> getPermissionById(@PathVariable Long id) {
		Optional<PaymentMethod> payment = paymentMethodService.getPaymentMethod(id);
		if (payment.isPresent()) {
			return new ResponseEntity<>(payment.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/active-names")
	public ResponseEntity<List<String>> getAllActivePaymentMethodNames() {
		List<String> activePaymentNames = paymentMethodService.getAllActivePaymentMethodNames();
		return new ResponseEntity<>(activePaymentNames, HttpStatus.OK);
	}

	@PutMapping("/update-status/{id}")
	public ResponseEntity<String> updatePaymentMethodStatus(@PathVariable Long id, @RequestParam boolean isActive) {

		paymentMethodService.updatePaymentMethodStatus(id, isActive);
		return new ResponseEntity<>("Payment method status updated successfully.", HttpStatus.OK);
	}

}
