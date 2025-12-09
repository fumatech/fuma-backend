package com.backend.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PurchaseDIOrder;
import com.backend.Entity.PurchasePoOrder;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Repository.PurchasePoOrderRepo;

@Service
public class CombinedPurchaseOrderService {

	@Autowired
	private PurchasePoOrderRepo purchasePoOrderRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIOrderRepo;

	public Map<String, List<Object>> getPurchasesByVendor(String vendorName) {
		Map<String, List<Object>> vendorWiseOrders = new HashMap<>();

		List<PurchasePoOrder> poOrders = purchasePoOrderRepo.findByVendorName(vendorName);
		vendorWiseOrders.put(vendorName, new ArrayList<>(poOrders));

		List<PurchaseDIOrder> diOrders = purchaseDIOrderRepo.findByVendorName(vendorName);
		vendorWiseOrders.get(vendorName).addAll(diOrders);

		return vendorWiseOrders;
	}

	public Map<String, List<Object>> getAllPurchaseOrdersWithTax() {

		Map<String, List<Object>> result = new HashMap<>();

		List<Object> allOrders = new ArrayList<>();

		// PO orders with tax
		allOrders.addAll(purchasePoOrderRepo.findAllWithPurchaseTax());

		// DI orders with tax
		allOrders.addAll(purchaseDIOrderRepo.findAllWithPurchaseTax());

		result.put("orders", allOrders);

		return result;
	}
}
