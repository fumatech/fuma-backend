package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Holiday;
import com.backend.Service.HolidayService;

import java.util.List;

@RestController
@RequestMapping("/holiday")
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
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    // Create a new holiday
    @PostMapping("/add")
    public ResponseEntity<Holiday> saveHoliday(@RequestBody Holiday holiday) {
        Holiday createdHoliday = holidayService.saveHoliday(holiday);
        return new ResponseEntity<>(createdHoliday, HttpStatus.CREATED);
    }

    // Get all holidays
    @GetMapping("/getall")
    public ResponseEntity<List<Holiday>> getAllHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    // Get holiday by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Holiday> getHolidayById(@PathVariable Long id) {
        Holiday holiday = holidayService.getHolidayById(id);
        if (holiday != null) {
            return new ResponseEntity<>(holiday, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update holiday by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday updatedHoliday) {
        Holiday holiday = holidayService.updateHoliday(id, updatedHoliday);
        if (holiday != null) {
            return new ResponseEntity<>(holiday, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete holiday by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHolidayById(@PathVariable Long id) {
        holidayService.deleteHolidayById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
