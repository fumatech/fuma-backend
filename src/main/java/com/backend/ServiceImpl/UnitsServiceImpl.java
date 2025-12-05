package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Units;
import com.backend.Repository.UnitRepo;
import com.backend.Service.UnitsService;

@Service
public class UnitsServiceImpl implements UnitsService {

	
	@Autowired
	private UnitRepo unitrepo;
	
	@Override
	public Units saveUnits(Units units) {
		// TODO Auto-generated method stub
		return unitrepo.save(units);
	}

	@Override
	public List<Units> getAllUnits() {
		// TODO Auto-generated method stub
		return unitrepo.findAll();
	}

	@Override
	public Units getById(Long id) {
		// TODO Auto-generated method stub
		return unitrepo.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		unitrepo.deleteById(id);

	}

}
