package com.backend.ServiceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PurchaseSaleSummaryDTO;
import com.backend.Repository.FranchisePurchaseReturnRepo;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Repository.PurchasePoOrderRepo;
import com.backend.Repository.PurchaseReturnRepo;
import com.backend.Repository.SaleDIOrderRepo;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Repository.TransactionRepo;

@Service
public class PurchaseSaleSummaryService {

	@Autowired
	private PurchasePoOrderRepo purchasePoRepo;
	@Autowired
	private PurchaseDIOrderRepo purchaseDIRepo;
	@Autowired
	private SaleSoOrderRepo saleSoRepo;
	@Autowired
	private SaleDIOrderRepo saleDIRepo;
	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;
	@Autowired
	private FranchisePurchaseReturnRepo franchisePurchaseReturnRepo;
	@Autowired
	private TransactionRepo transactionRepo;

	public PurchaseSaleSummaryDTO getSummary() {

		PurchaseSaleSummaryDTO dto = new PurchaseSaleSummaryDTO();

		// ================= PURCHASE =================

		BigDecimal purchasePo = purchasePoRepo.totalPurchasePo();
		BigDecimal purchasePoTax = purchasePoRepo.totalPurchasePoWithTax();

		BigDecimal purchaseDi = purchaseDIRepo.totalPurchaseDI();
		BigDecimal purchaseDiTax = purchaseDIRepo.totalPurchaseDIWithTax();

		BigDecimal totalPurchase = purchasePo.add(purchaseDi);
		BigDecimal totalPurchaseWithTax = purchasePoTax.add(purchaseDiTax);

		BigDecimal purchaseReturn = purchaseReturnRepo.totalPurchaseReturnWithTax();

		BigDecimal purchasePaid = transactionRepo.totalPurchasePaid();

		BigDecimal purchaseDue = totalPurchaseWithTax.subtract(purchaseReturn).subtract(purchasePaid);

		// ================= SALE =================

		BigDecimal saleSo = saleSoRepo.totalSaleSo();
		BigDecimal saleSoTax = saleSoRepo.totalSaleSoWithTax();

		BigDecimal saleDi = saleDIRepo.totalSaleDI();
		BigDecimal saleDiTax = saleDIRepo.totalSaleDIWithTax();

		BigDecimal totalSale = saleSo.add(saleDi);
		BigDecimal totalSaleWithTax = saleSoTax.add(saleDiTax);

		// ✅ Sale Return = Franchise Purchase Return
		BigDecimal saleReturn = franchisePurchaseReturnRepo.totalFranchisePurchaseReturnWithTax();

		BigDecimal saleReceived = transactionRepo.totalSaleReceived();

		BigDecimal saleDue = totalSaleWithTax.subtract(saleReturn).subtract(saleReceived);

		// ================= DTO SET =================

		dto.setTotalPurchase(totalPurchase);
		dto.setPurchaseIncludingTax(totalPurchaseWithTax);
		dto.setTotalPurchaseReturnIncludingTax(purchaseReturn);
		dto.setPurchaseDue(purchaseDue);

		dto.setTotalSale(totalSale);
		dto.setSaleIncludingTax(totalSaleWithTax);
		dto.setTotalSaleReturnIncludingTax(saleReturn);
		dto.setSaleDue(saleDue);

		// ================= OVERALL =================

		dto.setSaleMinusPurchase(totalSale.subtract(totalPurchase));

		dto.setDueAmount(saleDue.subtract(purchaseDue));

		return dto;
	}
}
