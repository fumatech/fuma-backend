package com.backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Entity.ProductPurchaseReportDTO;
import com.backend.Repository.PurchaseDIItemRepo;
import com.backend.Repository.PurchasePoItemRepo;
import com.backend.Repository.StockAdjustmentItemsRepo;

@Service
public class ProductPurchaseReportService {

	private final PurchaseDIItemRepo diItemRepo;
	private final PurchasePoItemRepo poItemRepo;
	private final StockAdjustmentItemsRepo stockAdjustmentRepo;

	public ProductPurchaseReportService(PurchaseDIItemRepo diItemRepo, PurchasePoItemRepo poItemRepo,
			StockAdjustmentItemsRepo stockAdjustmentRepo) {
		this.diItemRepo = diItemRepo;
		this.poItemRepo = poItemRepo;
		this.stockAdjustmentRepo = stockAdjustmentRepo;
	}

	public List<ProductPurchaseReportDTO> getAllProductPurchaseReport() {
		List<ProductPurchaseReportDTO> report = new ArrayList<>();

		report.addAll(diItemRepo.fetchAllDIItems());
		report.addAll(poItemRepo.fetchAllPOItems());
		report.addAll(stockAdjustmentRepo.fetchAllStockAdjustmentAsPurchaseReport());

		return report;
	}
}
