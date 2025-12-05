package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Leave;
import com.backend.Service.LeaveService;

@RestController
@RequestMapping("/leave")
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
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // Endpoint to save a new leave
    @PostMapping("/add")
    public ResponseEntity<Leave> saveLeave(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.saveLeave(leave);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);  // Return created status
    }

    // Endpoint to get all leaves
    @GetMapping("/getall")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        List<Leave> leaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(leaves, HttpStatus.OK);  // Return OK status with leaves
    }

    // Endpoint to get a leave by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Long id) {
        Leave leave = leaveService.getLeaveById(id);
        if (leave != null) {
            return new ResponseEntity<>(leave, HttpStatus.OK);  // Return OK status with leave
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return NOT_FOUND if not found
        }
    }

    // Endpoint to update a leave by ID
    @PutMapping("update/{id}")
    public ResponseEntity<Leave> updateLeave(@PathVariable Long id, @RequestBody Leave updatedLeave) {
        Leave leave = leaveService.updateLeave(id, updatedLeave);
        if (leave != null) {
            return new ResponseEntity<>(leave, HttpStatus.OK);  // Return OK status with updated leave
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return NOT_FOUND if not found
        }
    }
        
    // Endpoint to delete a leave by ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteLeaveById(@PathVariable Long id) {
        leaveService.deleteLeaveById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return NO_CONTENT status after deletion
    }
}
