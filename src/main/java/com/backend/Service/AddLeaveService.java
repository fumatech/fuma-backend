package com.backend.Service;

import java.util.List;

import com.backend.Entity.AddLeave;

public interface AddLeaveService {
	
	AddLeave saveLeave(AddLeave addLeave);
	
	List<AddLeave> getAllLeaves();

	AddLeave updateLeave(Long id, AddLeave updatedLeave);

	AddLeave getLeaveById(Long id);

	void deleteLeaveById(Long id);

	
	
}
