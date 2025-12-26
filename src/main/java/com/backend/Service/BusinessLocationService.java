package com.backend.Service;

import java.util.List;

import com.backend.Entity.BusinessLocation;

public interface BusinessLocationService {

	BusinessLocation save(BusinessLocation businessLocation);

	BusinessLocation getById(Long id);

	List<BusinessLocation> getAll();

	BusinessLocation update(Long id, BusinessLocation businessLocation);

	void delete(Long id);
}
