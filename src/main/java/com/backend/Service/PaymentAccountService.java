package com.backend.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.backend.Entity.PaymentAccount;
import com.backend.Entity.Transaction;

public interface PaymentAccountService {
	
	PaymentAccount savePaymentAccount(PaymentAccount paymentAccount);
	
	List<PaymentAccount> getAllPaymentAccounts();
	
	Optional<PaymentAccount> getPaymentAccountById(Long id);
	
	PaymentAccount updatePaymentAccount(Long id, PaymentAccount paymentAccount);
	
	void deletePaymentAccount(Long id);
	
	void updateAccountStatus(Long id, Long status);
	

    public Transaction createTransaction(Long accountId, Transaction transactionRequest);

    
    public BigDecimal getCurrentBalance(Long id);
    
    public Transaction updateTransaction(Long transactionId, BigDecimal amount, String paymentMethod, 
            String transactionType, String addedBy, String note, LocalDateTime date, String vendor) ;

    

}
