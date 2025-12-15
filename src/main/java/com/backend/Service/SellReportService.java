package com.backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.SellReportDTO;
import com.backend.Repository.SaleDIItemRepo;
import com.backend.Repository.SaleSoItemRepo;

@Service
public class SellReportService {

	@Autowired
	private SaleSoItemRepo saleSoItemRepo;

	@Autowired
	private SaleDIItemRepo saleDIItemRepo;

	public List<SellReportDTO> getSellReport() {

		List<SellReportDTO> report = new ArrayList<>();

		report.addAll(saleSoItemRepo.getSoSellReport());
		report.addAll(saleDIItemRepo.getDiSellReport());

		return report;
	}
}
