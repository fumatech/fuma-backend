package com.backend.Controller;

import com.backend.Entity.AddLeave;
import com.backend.Service.AddLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/add-leave")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)

public class AddLeaveController {

	@Autowired
	private AddLeaveService addLeaveService;

	// Create a new leave request
	@PostMapping("/add")
	public ResponseEntity<AddLeave> addLeave(@RequestBody AddLeave addLeave) {
		AddLeave savedLeave = addLeaveService.saveLeave(addLeave);
		return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
	}

	// Get all leave requests
	@GetMapping("/getall")
	public ResponseEntity<List<AddLeave>> getAllLeaves() {
		List<AddLeave> leaves = addLeaveService.getAllLeaves();
		return new ResponseEntity<>(leaves, HttpStatus.OK);
	}

	// Get leave request by ID
	@GetMapping("/get/{id}")
	public ResponseEntity<AddLeave> getLeaveById(@PathVariable Long id) {
		AddLeave leave = addLeaveService.getLeaveById(id);
		if (leave != null) {
			return new ResponseEntity<>(leave, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Update a leave request by ID
	@PutMapping("/update/{id}")
	public ResponseEntity<AddLeave> updateLeave(@PathVariable Long id, @RequestBody AddLeave updatedLeave) {
		AddLeave leave = addLeaveService.updateLeave(id, updatedLeave);
		if (leave != null) {
			return new ResponseEntity<>(leave, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Delete a leave request by ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
		addLeaveService.deleteLeaveById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
