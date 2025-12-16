package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.BrandWiseReportDTO;
import com.backend.Entity.CategoryWiseReportDTO;
import com.backend.Entity.GroupedDateReportDTO;
import com.backend.Service.ReportService;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ReportController {

	@Autowired
	private ReportService reportService;

	// 🔹 BRAND WISE REPORT
	@GetMapping("/brand-wise")
	public List<BrandWiseReportDTO> getBrandWiseReport() {
		return reportService.getBrandWiseReport();
	}

	// 🔹 CATEGORY WISE REPORT
	@GetMapping("/category-wise")
	public List<CategoryWiseReportDTO> getCategoryWiseReport() {
		return reportService.getCategoryWiseReport();
	}

	@GetMapping("/date-wise")
	public List<GroupedDateReportDTO> getGroupedDateReport() {
		return reportService.getGroupedDateReport();
	}
}
