package com.backend.Service;

import java.util.List;

import com.backend.Entity.Brands;

public interface BrandsService {
	
	Brands saveBrands(Brands brands);
	
	List<Brands> getAllBrands();
	
	    Brands getById(Long id);
	    void deleteById(Long id);

}
