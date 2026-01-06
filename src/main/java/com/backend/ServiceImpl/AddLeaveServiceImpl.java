package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.AddLeave;
import com.backend.Repository.AddLeaveRepo;
import com.backend.Service.AddLeaveService;

@Service
public class AddLeaveServiceImpl implements AddLeaveService {

	@Autowired
	private AddLeaveRepo addLeaveRepo;

	@Override
	public AddLeave saveLeave(AddLeave addLeave) {
		return addLeaveRepo.save(addLeave);
	}

	@Override
	public List<AddLeave> getAllLeaves() {
		return addLeaveRepo.findAll();
	}

	@Override
	public AddLeave updateLeave(Long id, AddLeave updatedLeave) {
		Optional<AddLeave> existingLeave = addLeaveRepo.findById(id);
		if (existingLeave.isPresent()) {
			AddLeave leave = existingLeave.get();
			leave.setEmployee(updatedLeave.getEmployee());
			leave.setLeaveType(updatedLeave.getLeaveType());
			leave.setStartDate(updatedLeave.getStartDate());
			leave.setEndDate(updatedLeave.getEndDate());
			leave.setReason(updatedLeave.getReason());
			leave.setStatus(updatedLeave.getStatus());
			return addLeaveRepo.save(leave);
		}
		return null; // Return null if the leave with the given ID does not exist
	}

	@Override
	public AddLeave getLeaveById(Long id) {
		return addLeaveRepo.findById(id).orElse(null);
	}

	@Override
	public void deleteLeaveById(Long id) {
		addLeaveRepo.deleteById(id);
	}

	@Override
	public AddLeave updateLeaveStatus(Long id, long status) {
		return addLeaveRepo.findById(id).map(leave -> {
			leave.setStatus(status); // Only change status
			return addLeaveRepo.save(leave);
		}).orElse(null);
	}

}
