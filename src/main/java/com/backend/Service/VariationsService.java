package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Variations;

public interface VariationsService {
	Variations saveVariations(Variations variations);

    List<Variations> getAll();

    Optional<Variations> getById(Long id);

    void deleteById(Long id);

    Variations updateVariation(Long id, Variations updatedVariation);
}

