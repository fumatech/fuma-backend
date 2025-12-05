package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Brands;
import com.backend.Repository.BrandRepo;
import com.backend.Service.BrandsService;
@Service
public class BrandsServiceImpl implements BrandsService {

	@Autowired
	private BrandRepo brandrepo;
	
	@Override
	public Brands saveBrands(Brands brands) {
		return brandrepo.save(brands);
	}

	@Override
	public List<Brands> getAllBrands() {
		return brandrepo.findAll();
	}

	@Override
	public Brands getById(Long id) {
		return brandrepo.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		brandrepo.deleteById(id);

	}

}
