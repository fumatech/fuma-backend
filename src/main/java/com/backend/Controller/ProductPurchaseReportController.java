package com.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.ProductPurchaseReportDTO;
import com.backend.Service.ProductPurchaseReportService;

@RestController
@RequestMapping("/ProductPurchaseReport")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ProductPurchaseReportController {

	private final ProductPurchaseReportService reportService;

	public ProductPurchaseReportController(ProductPurchaseReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/getall")
	public List<ProductPurchaseReportDTO> getAllProductPurchaseReport() {
		return reportService.getAllProductPurchaseReport();
	}
}
