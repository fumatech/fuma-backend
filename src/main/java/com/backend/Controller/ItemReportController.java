package com.backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.ItemReportDTO;
import com.backend.Service.ItemReportService;

@RestController
@RequestMapping("/itemReport")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ItemReportController {

	private final ItemReportService itemReportService;

	public ItemReportController(ItemReportService itemReportService) {
		this.itemReportService = itemReportService;
	}

	@GetMapping("/getall")
	public List<ItemReportDTO> getAll() {
		return itemReportService.getAllItemReports();
	}
}
