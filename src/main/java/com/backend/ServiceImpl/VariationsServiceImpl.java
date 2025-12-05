package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Variations;
import com.backend.Repository.VariationsRepo;
import com.backend.Service.VariationsService;
@Service
public class VariationsServiceImpl implements VariationsService {

	 @Autowired
	    private VariationsRepo varirepo;

	    @Override
	    public Variations saveVariations(Variations variations) {
	        return varirepo.save(variations);
	    }

	    @Override
	    public List<Variations> getAll() {
	        return varirepo.findAll();
	    }

	    @Override
	    public Optional<Variations> getById(Long id) {
	        return varirepo.findById(id);
	    }

	    @Override
	    public void deleteById(Long id) {
	        varirepo.deleteById(id);
	    }

	    @Override
	    public Variations updateVariation(Long id, Variations updatedVariation) {
	        // Check if the variation exists
	        return varirepo.findById(id).map(existingVariation -> {
	            existingVariation.setVariationName(updatedVariation.getVariationName());
	            existingVariation.setValues(updatedVariation.getValues());
	            return varirepo.save(existingVariation);
	        }).orElseThrow();
	    }
}