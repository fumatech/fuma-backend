package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.ClientLedgerDTO;
import com.backend.Entity.Customer;
import com.backend.Repository.CustomerRepo;
import com.backend.Repository.FranchisePurchaseReturnRepo;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Repository.PurchasePoOrderRepo;
import com.backend.Repository.PurchaseReturnRepo;
import com.backend.Repository.SaleDIOrderRepo;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Repository.TransactionRepo;

@Service
public class ClientLedgerService {

	@Autowired
	private PurchasePoOrderRepo purchasePoRepo;
	@Autowired
	private PurchaseDIOrderRepo purchaseDIRepo;
	@Autowired
	private SaleSoOrderRepo saleSoRepo;
	@Autowired
	private SaleDIOrderRepo saleDIRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;
	@Autowired
	private FranchisePurchaseReturnRepo franchisePurchaseReturnRepo;
	@Autowired
	private CustomerRepo customerRepo;

	public List<ClientLedgerDTO> getClientLedger() {

		Map<String, ClientLedgerDTO> ledgerMap = new HashMap<>();

		purchasePoRepo.findAll().forEach(p -> {
			if (p.getVendor() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, p.getVendor());
			ledger.setTotalPurchase(ledger.getTotalPurchase().add(getOrZero(p.getNetTotalAmount())));
		});

		purchaseDIRepo.findAll().forEach(p -> {
			if (p.getVendor() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, p.getVendor());
			ledger.setTotalPurchase(ledger.getTotalPurchase().add(getOrZero(p.getNetTotalAmount())));
		});

		purchaseReturnRepo.findAll().forEach(pr -> {
			if (pr.getVendor() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, pr.getVendor());
			ledger.setTotalPurchaseReturn(ledger.getTotalPurchaseReturn().add(getOrZero(pr.getTotalAmount())));
		});

		saleSoRepo.findAll().forEach(s -> {
			if (s.getFranchise() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, s.getFranchise());
			ledger.setTotalSale(ledger.getTotalSale().add(getOrZero(s.getNetTotalAmount())));
		});

		saleDIRepo.findAll().forEach(s -> {
			if (s.getFranchise() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, s.getFranchise());
			ledger.setTotalSale(ledger.getTotalSale().add(getOrZero(s.getNetTotalAmount())));
		});

		franchisePurchaseReturnRepo.findAll().forEach(fr -> {

			if (fr.getFranchiseId() == null)
				return;

			Customer customer = customerRepo.findByFranchiseId(fr.getFranchiseId()).orElse(null);

			if (customer == null || customer.getFranchiseName() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, customer.getFranchiseName());
			ledger.setTotalSaleReturn(ledger.getTotalSaleReturn().add(getOrZero(fr.getNetTotalAmount())));
		});

		transactionRepo.findAll().forEach(t -> {

			String contact = t.getVendor() != null ? t.getVendor() : t.getFranchiseName();

			if (contact == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, contact);
			ledger.setPaidAmount(ledger.getPaidAmount().add(getOrZero(t.getAmount())));
		});

		ledgerMap.values().forEach(l -> {

			BigDecimal purchaseNet = l.getTotalPurchase().subtract(l.getTotalPurchaseReturn());

			BigDecimal saleNet = l.getTotalSale().subtract(l.getTotalSaleReturn());

			BigDecimal due = purchaseNet.add(saleNet).subtract(l.getPaidAmount());

			l.setDue(due);
		});

		return new ArrayList<>(ledgerMap.values());
	}

	private ClientLedgerDTO getLedger(Map<String, ClientLedgerDTO> map, String contact) {
		return map.computeIfAbsent(contact, c -> createLedger(c));
	}

	private ClientLedgerDTO createLedger(String contact) {
		ClientLedgerDTO dto = new ClientLedgerDTO();
		dto.setContact(contact);
		dto.setTotalPurchase(BigDecimal.ZERO);
		dto.setTotalPurchaseReturn(BigDecimal.ZERO);
		dto.setTotalSale(BigDecimal.ZERO);
		dto.setTotalSaleReturn(BigDecimal.ZERO);
		dto.setPaidAmount(BigDecimal.ZERO);
		dto.setDue(BigDecimal.ZERO);
		return dto;
	}

	private BigDecimal getOrZero(BigDecimal val) {
		return val == null ? BigDecimal.ZERO : val;
	}
}
