package com.backend.Service;

import java.util.List;

import com.backend.Entity.BusinessCategory;

public interface BusinessCategoryService {

	BusinessCategory save(BusinessCategory category);

	List<BusinessCategory> getAll();

	BusinessCategory getById(Long id);

	BusinessCategory update(Long id, BusinessCategory category);

	void delete(Long id);
}
