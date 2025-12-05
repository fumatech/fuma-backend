package com.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Repository.FranchisePurchaseOrderRepo;
import com.backend.Repository.FranchisePurchaseReturnRepo;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Repository.PurchaseOrderRepo;
import com.backend.Repository.PurchaseReturnRepo;
import com.backend.Repository.SaleDIOrderRepo;

@Service
public class IdGenerator {

	@Autowired
	private PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIOrderRepo;

	@Autowired
	private SaleDIOrderRepo saleDIOrderRepo;

	@Autowired

	private PurchaseReturnRepo PurchaseReturnRepo;

	@Autowired
	private FranchisePurchaseOrderRepo franchisePurchaseOrderRepo;

	@Autowired
	private FranchisePurchaseReturnRepo franchisePurchaseReturnRepo;

	public synchronized String generatePurchaseOrderId() {
		Long lastId = purchaseOrderRepo.getLastPurchaseOrderId();
		Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
		return "FUMAPO" + nextId;
	}

	public synchronized String generatePurchaseDIOrderId() {
		Long lastId = purchaseDIOrderRepo.getLastPurchaseDIOrderId();
		Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
		return "FUMAPDI" + nextId;
	}

	public synchronized String generateSaleDIOrderId() {
		Long lastId = saleDIOrderRepo.getLastSaleDIOrderId();
		Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
		return "FUMADISO" + nextId;
	}

	public synchronized String generatePurchaseReturnId() {
		Long lastId = PurchaseReturnRepo.getLastPurchaseReturnId();
		Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
		return "FUMAPR" + nextId;
	}

	public synchronized String generateFranchisePurchaseOrderId() {
		Long lastId = franchisePurchaseOrderRepo.getLastFranchisePurchaseOrderId();
		Long nextId = (lastId != null) ? lastId + 1 : 1;
		return "FUMAFPO" + nextId;
	}

	public synchronized String generateFranchisePurchaseReturnId() {
		Long lastId = franchisePurchaseReturnRepo.getLastFranchisePurchaseReturnId();
		Long nextId = (lastId != null) ? lastId + 1 : 1;
		return "FUMAFPRO" + nextId;
	}

	public synchronized String generatePurchaseReturnInvoiceNumber() {
		String lastInvoice = PurchaseReturnRepo.getLastInvoiceNumber();

		if (lastInvoice != null && lastInvoice.startsWith("PRInvoice")) {
			// Extract the numeric part
			String numberPart = lastInvoice.replace("PRInvoice", "");
			int nextNumber = Integer.parseInt(numberPart) + 1;

			// Format with leading zero if needed (e.g., 01, 02, 03...)
			return String.format("PRInvoice%02d", nextNumber);
		} else {
			return "PRInvoice01"; // First invoice number
		}
	}

	public synchronized String generateFranchisePurchaseReturnInvoiceNumber() {
		String lastInvoice = franchisePurchaseReturnRepo.getLastInvoiceNumber();

		if (lastInvoice != null && lastInvoice.startsWith("FRPRInvoice")) {
			// Extract numeric part
			String numberPart = lastInvoice.replace("FRPRInvoice", "");
			int nextNumber = Integer.parseInt(numberPart) + 1;

			// Format with leading zeros if required (e.g., 01, 02, 03…)
			return String.format("FRPRInvoice%03d", nextNumber);
		} else {
			return "FRPRInvoice01"; // First invoice number
		}
	}

}
