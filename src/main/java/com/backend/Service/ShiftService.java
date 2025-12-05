package com.backend.Service;

import java.util.List;
import com.backend.Entity.Shift;

public interface ShiftService {
	
	Shift saveShift(Shift shift);
	
	List<Shift> getAllShifts();

	Shift updateShift(Long id, Shift updatedShift);

	Shift getShiftById(Long id);

	void deleteShiftById(Long id);

}
