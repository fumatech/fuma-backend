package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.SellReportDTO;
import com.backend.Service.SellReportService;

@RestController
@RequestMapping("/sell-report")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class SellReportController {

	@Autowired
	private SellReportService sellReportService;

	@GetMapping("/getall")
	public List<SellReportDTO> getSellReport() {
		return sellReportService.getSellReport();
	}
}
