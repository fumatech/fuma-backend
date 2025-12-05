package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.JournalEntry;
import com.backend.ServiceImpl.TrailBalanceService;

@RestController
@RequestMapping("/trail-balance")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class TrailBalanceController {
	
	 @Autowired
	    private TrailBalanceService service;

	    @GetMapping("/get")
	    public List<JournalEntry> getTrailBalance() {
	        return service.generateTrailBalance();
	    }

}
