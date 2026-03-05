package com.backend.Controller;

import com.backend.Entity.PerformanceFeedback;
import com.backend.Service.PerformanceFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance-feedback")
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
public class PerformanceFeedbackController {

    private final PerformanceFeedbackService feedbackService;

    @Autowired
    public PerformanceFeedbackController(PerformanceFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Add new feedback
    @PostMapping("/add")
    public ResponseEntity<PerformanceFeedback> addFeedback(@RequestBody PerformanceFeedback feedback) {
        PerformanceFeedback created = feedbackService.save(feedback);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all feedbacks
    @GetMapping("/getall")
    public ResponseEntity<List<PerformanceFeedback>> getAllFeedbacks() {
        List<PerformanceFeedback> feedbacks = feedbackService.getAll();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedback by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<PerformanceFeedback> getFeedbackById(@PathVariable("id") Long id) {
        PerformanceFeedback feedback = feedbackService.getById(id);
        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    // Get feedbacks by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceFeedback>> getByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        List<PerformanceFeedback> feedbacks = feedbackService.getByEmployeeId(employeeId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedbacks by month and year
    @GetMapping("/period/{month}/{year}")
    public ResponseEntity<List<PerformanceFeedback>> getByMonthAndYear(
            @PathVariable("month") int month,
            @PathVariable("year") int year) {
        List<PerformanceFeedback> feedbacks = feedbackService.getByMonthAndYear(month, year);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedbacks by year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<PerformanceFeedback>> getByYear(@PathVariable("year") int year) {
        List<PerformanceFeedback> feedbacks = feedbackService.getByYear(year);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedbacks by employee, month and year
    @GetMapping("/employee/{employeeId}/{month}/{year}")
    public ResponseEntity<List<PerformanceFeedback>> getByEmployeeAndPeriod(
            @PathVariable("employeeId") Long employeeId,
            @PathVariable("month") int month,
            @PathVariable("year") int year) {
        List<PerformanceFeedback> feedbacks = feedbackService.getByEmployeeIdAndMonthAndYear(employeeId, month, year);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Update feedback
    @PutMapping("/update/{id}")
    public ResponseEntity<PerformanceFeedback> updateFeedback(@PathVariable("id") Long id, @RequestBody PerformanceFeedback updatedFeedback) {
        PerformanceFeedback feedback = feedbackService.update(id, updatedFeedback);
        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    // Delete feedback
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable("id") Long id) {
        boolean isDeleted = feedbackService.deleteById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
