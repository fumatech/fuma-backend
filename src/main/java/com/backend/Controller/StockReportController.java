package com.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.StockReportDTO;
import com.backend.ServiceImpl.StockReportService;

@RestController
@RequestMapping("/stock-report")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class StockReportController {

	private final StockReportService stockReportService;

	public StockReportController(StockReportService stockReportService) {
		this.stockReportService = stockReportService;
	}

	@GetMapping("/report")
	public List<StockReportDTO> getStockReport() {
		return stockReportService.getStockReport();
	}
}
