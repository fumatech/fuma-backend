package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.PaymentMethod;

public interface PaymentMethodService {
	
	PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);
	
	List<PaymentMethod> getAllPaymentMethod();
	
	Optional<PaymentMethod> getPaymentMethod(Long id);
	
	List<String> getAllActivePaymentMethodNames();

    void updatePaymentMethodStatus(Long id, boolean isActive);
	

}
