package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.Warehouse;
import com.backend.Repository.WarehouseRepo;
import com.backend.Service.WarehouseService;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepo warehouseRepo;

	@Override
	public Warehouse save(Warehouse warehouse) {
		warehouse.setId(null);
		if (warehouse.getIsActive() == null) {
			warehouse.setIsActive(true);
		}

		if (warehouseRepo.count() == 0) {
			warehouse.setIsDefault(true);
		}

		if (Boolean.TRUE.equals(warehouse.getIsDefault())) {
			resetDefaultWarehouse();
		}

		return warehouseRepo.save(warehouse);
	}

	@Override
	public List<Warehouse> getAll() {
		return warehouseRepo.findAll();
	}

	@Override
	public Warehouse getById(Long id) {
		return warehouseRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
	}

	@Override
	public Warehouse update(Long id, Warehouse warehouse) {
		Warehouse existing = getById(id);
		existing.setName(warehouse.getName());
		existing.setLocation(warehouse.getLocation());
		existing.setContactDetails(warehouse.getContactDetails());
		existing.setUsername(warehouse.getUsername());
		existing.setPassword(warehouse.getPassword());
		existing.setIsActive(warehouse.getIsActive() == null ? existing.getIsActive() : warehouse.getIsActive());

		if (Boolean.TRUE.equals(warehouse.getIsDefault())) {
			resetDefaultWarehouse();
			existing.setIsDefault(true);
		}

		return warehouseRepo.save(existing);
	}

	@Override
	public void delete(Long id) {
		warehouseRepo.deleteById(id);
	}

	@Override
	public Optional<Warehouse> authenticate(String username, String password) {
		return warehouseRepo.findByUsernameAndPasswordAndIsActiveTrue(username, password);
	}

	private void resetDefaultWarehouse() {
		warehouseRepo.findByIsDefaultTrue().ifPresent(existingDefault -> {
			existingDefault.setIsDefault(false);
			warehouseRepo.save(existingDefault);
		});
	}
}

