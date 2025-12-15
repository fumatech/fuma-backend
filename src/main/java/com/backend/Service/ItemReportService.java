package com.backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Entity.ItemReportDTO;
import com.backend.Repository.ItemReportRepo;

@Service
public class ItemReportService {

	private final ItemReportRepo itemReportRepo;

	public ItemReportService(ItemReportRepo itemReportRepo) {
		this.itemReportRepo = itemReportRepo;
	}

	public List<ItemReportDTO> getAllItemReports() {

		List<ItemReportDTO> combined = new ArrayList<>();

		combined.addAll(itemReportRepo.fetchPurchaseDI());
		combined.addAll(itemReportRepo.fetchPurchasePO());
		combined.addAll(itemReportRepo.fetchSaleDI());
		combined.addAll(itemReportRepo.fetchSaleSO());

		return combined;
	}
}
