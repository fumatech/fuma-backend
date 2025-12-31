package com.backend.Service;

import java.util.List;

import com.backend.Entity.StockTransfer;

public interface StockTransferService {

	StockTransfer save(StockTransfer stockTransfer);

	StockTransfer getById(Long id);

	List<StockTransfer> getAll();

	StockTransfer update(Long id, StockTransfer stockTransfer);

	void delete(Long id);
}
