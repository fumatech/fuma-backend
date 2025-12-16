package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.BrandWiseReportDTO;
import com.backend.Entity.CategoryWiseReportDTO;
import com.backend.Entity.GroupedDateReportDTO;
import com.backend.Repository.ProductRepo;
import com.backend.Repository.ReportRepo;
import com.backend.Service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ReportRepo reportRepo;

	@Override
	public List<GroupedDateReportDTO> getGroupedDateReport() {
		return reportRepo.getProductSalesGroupedByDate();
	}

	@Override
	public List<BrandWiseReportDTO> getBrandWiseReport() {
		return productRepo.getBrandWiseReport();
	}

	@Override
	public List<CategoryWiseReportDTO> getCategoryWiseReport() {
		return productRepo.getCategoryWiseReport();
	}
}
