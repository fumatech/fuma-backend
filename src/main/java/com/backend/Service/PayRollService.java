package com.backend.Service;

import java.util.List;

import com.backend.Entity.PayRoll;

public interface PayRollService {
	
	PayRoll savePayRoll(PayRoll payRoll);
	
	List<PayRoll> getAllPayRolls();

	PayRoll updatePayRoll(Long id, PayRoll updatedPayRoll);

	PayRoll getPayRollById(Long id);

	void deletePayRollById(Long id);

}
