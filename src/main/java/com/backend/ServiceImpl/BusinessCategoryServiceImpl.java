package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.BusinessCategory;
import com.backend.Repository.BusinessCategoryRepo;
import com.backend.Service.BusinessCategoryService;

@Service
public class BusinessCategoryServiceImpl implements BusinessCategoryService {

	@Autowired
	private BusinessCategoryRepo repo;

	@Override
	public BusinessCategory save(BusinessCategory category) {
		return repo.save(category);
	}

	@Override
	public List<BusinessCategory> getAll() {
		return repo.findAll();
	}

	@Override
	public BusinessCategory getById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("BusinessCategory not found with id: " + id));
	}

	@Override
	public BusinessCategory update(Long id, BusinessCategory category) {
		BusinessCategory existing = getById(id);
		existing.setName(category.getName());
		existing.setLocationId(category.getLocationId());
		return repo.save(existing);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
