package com.backend.Service;

import java.util.List;

import com.backend.Entity.Leave;


public interface LeaveService {
	
	Leave saveLeave(Leave leave);
	
	List<Leave> getAllLeaves();

	Leave updateLeave(Long id, Leave updatedLeave);

	Leave getLeaveById(Long id);

	void deleteLeaveById(Long id);

}
