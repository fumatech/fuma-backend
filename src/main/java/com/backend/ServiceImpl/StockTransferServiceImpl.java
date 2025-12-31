package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.StockTransaction;
import com.backend.Entity.StockTransfer;
import com.backend.Entity.StockTransferItems;
import com.backend.Repository.StockTransferRepo;
import com.backend.Service.StockTransferService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StockTransferServiceImpl implements StockTransferService {

	@Autowired
	private StockTransferRepo stockTransferRepo;

	@Override
	public StockTransfer save(StockTransfer stockTransfer) {

		if (stockTransfer.getStockTransferItems() != null) {
			stockTransfer.getStockTransferItems().forEach(i -> i.setStockTransfer(stockTransfer));
		}

		if (stockTransfer.getStockTransactions() != null) {
			stockTransfer.getStockTransactions().forEach(t -> t.setStockTransfer(stockTransfer));
		}

		return stockTransferRepo.save(stockTransfer);
	}

	@Override
	public StockTransfer getById(Long id) {
		return stockTransferRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("StockTransfer not found with id " + id));
	}

	@Override
	public List<StockTransfer> getAll() {
		return stockTransferRepo.findAll();
	}

	@Override
	@Transactional
	public StockTransfer update(Long id, StockTransfer stockTransfer) {

		StockTransfer existing = getById(id);

		existing.setDate(stockTransfer.getDate());
		existing.setReferenceNumber(stockTransfer.getReferenceNumber());
		existing.setStatus(stockTransfer.getStatus());
		existing.setLocationFrom(stockTransfer.getLocationFrom());
		existing.setLocationTo(stockTransfer.getLocationTo());
		existing.setShippingCharges(stockTransfer.getShippingCharges());
		existing.setTotalAmount(stockTransfer.getTotalAmount());
		existing.setNote(stockTransfer.getNote());

		/* ===== ITEMS ===== */
		if (existing.getStockTransferItems() != null) {
			existing.getStockTransferItems().clear();
		}

		if (stockTransfer.getStockTransferItems() != null) {
			for (StockTransferItems item : stockTransfer.getStockTransferItems()) {
				item.setStockTransfer(existing);
				existing.getStockTransferItems().add(item);
			}
		}

		/* ===== TRANSACTIONS ===== */
		if (existing.getStockTransactions() != null) {
			existing.getStockTransactions().clear();
		}

		if (stockTransfer.getStockTransactions() != null) {
			for (StockTransaction tx : stockTransfer.getStockTransactions()) {
				tx.setStockTransfer(existing);
				existing.getStockTransactions().add(tx);
			}
		}

		return stockTransferRepo.save(existing);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		StockTransfer existing = getById(id);
		stockTransferRepo.delete(existing);
	}
}
