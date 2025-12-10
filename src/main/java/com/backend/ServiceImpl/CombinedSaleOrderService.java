package com.backend.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.SaleDIOrder;
import com.backend.Entity.SaleSoOrder;
import com.backend.Repository.SaleDIOrderRepo;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Service.SaleDIOrderService;
import com.backend.Service.SaleSoOrderService;

@Service
public class CombinedSaleOrderService {

	@Autowired
	private SaleSoOrderService saleSoOrderService;

	@Autowired
	private SaleDIOrderService saleDIOrderService;

	@Autowired
	private SaleDIOrderRepo saleDIOrderRepo;

	@Autowired
	private SaleSoOrderRepo saleSoOrderRepo;

	public List<Object> getAllCombinedOrders() {
		List<Object> combinedOrders = new ArrayList<>();

		combinedOrders.addAll(saleSoOrderService.getAllSaleSoOrders());

		combinedOrders.addAll(saleDIOrderService.getAllSaleDIOrders());

		return combinedOrders;
	}

	public Map<String, List<Object>> getSaleByFranchise(String franchise) {
		Map<String, List<Object>> franchiseWiseOrders = new HashMap<>();

		List<SaleDIOrder> diOrders = saleDIOrderRepo.findByFranchise(franchise);
		franchiseWiseOrders.put(franchise, new ArrayList<>(diOrders));

		List<SaleSoOrder> soOrders = saleSoOrderRepo.findByFranchise(franchise);
		franchiseWiseOrders.get(franchise).addAll(soOrders);

		return franchiseWiseOrders;
	}

	public Map<String, List<Object>> getAllSaleOrdersWithTax() {

		Map<String, List<Object>> result = new HashMap<>();

		List<Object> allOrders = new ArrayList<>();

		// PO orders with tax
		allOrders.addAll(saleSoOrderRepo.findAllWithSaleTax());

		// DI orders with tax
		allOrders.addAll(saleDIOrderRepo.findAllWithSaleTax());

		result.put("orders", allOrders);

		return result;
	}
}
