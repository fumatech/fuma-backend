package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.EmployeeGrievance;
import com.backend.Service.EmployeeGrievanceService;

@RestController
@RequestMapping("/employee-grievance")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://fusionmastertech.com",
    "https://fusionmastertech.com",
    "http://www.fusionmastertech.com",
    "https://www.fusionmastertech.com"
}, allowCredentials = "true")
public class EmployeeGrievanceController {

    private final EmployeeGrievanceService grievanceService;

    @Autowired
    public EmployeeGrievanceController(EmployeeGrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeGrievance> addGrievance(@RequestBody EmployeeGrievance grievance) {
        EmployeeGrievance created = grievanceService.save(grievance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<EmployeeGrievance>> getAllGrievances() {
        List<EmployeeGrievance> grievances = grievanceService.getAll();
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeGrievance> getGrievanceById(@PathVariable("id") Long id) {
        EmployeeGrievance grievance = grievanceService.getById(id);
        if (grievance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grievance, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeGrievance>> getByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        List<EmployeeGrievance> grievances = grievanceService.getByEmployeeId(employeeId);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeGrievance>> getByStatus(@PathVariable("status") String status) {
        List<EmployeeGrievance> grievances = grievanceService.getByStatus(status);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EmployeeGrievance>> getByCategory(@PathVariable("category") String category) {
        List<EmployeeGrievance> grievances = grievanceService.getByCategory(category);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<EmployeeGrievance>> getByPriority(@PathVariable("priority") String priority) {
        List<EmployeeGrievance> grievances = grievanceService.getByPriority(priority);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/assigned/{assignedTo}")
    public ResponseEntity<List<EmployeeGrievance>> getByAssignedTo(@PathVariable("assignedTo") Long assignedTo) {
        List<EmployeeGrievance> grievances = grievanceService.getByAssignedTo(assignedTo);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/type/{feedbackType}")
    public ResponseEntity<List<EmployeeGrievance>> getByFeedbackType(@PathVariable("feedbackType") String feedbackType) {
        List<EmployeeGrievance> grievances = grievanceService.getByFeedbackType(feedbackType);
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeGrievance> updateGrievance(
            @PathVariable("id") Long id,
            @RequestBody EmployeeGrievance updatedGrievance) {
        EmployeeGrievance grievance = grievanceService.update(id, updatedGrievance);
        if (grievance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grievance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGrievance(@PathVariable("id") Long id) {
        boolean isDeleted = grievanceService.deleteById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
