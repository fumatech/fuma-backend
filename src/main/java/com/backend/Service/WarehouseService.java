package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Warehouse;

public interface WarehouseService {

	Warehouse save(Warehouse warehouse);

	List<Warehouse> getAll();

	Warehouse getById(Long id);

	Warehouse update(Long id, Warehouse warehouse);

	void delete(Long id);

	Optional<Warehouse> authenticate(String username, String password);
}

