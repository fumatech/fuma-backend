package com.backend.Service;

import java.util.List;

import com.backend.Entity.Units;

public interface UnitsService {

	Units saveUnits(Units units);

	List<Units> getAllUnits();

	Units getById(Long id);

	void deleteById(Long id);

}
