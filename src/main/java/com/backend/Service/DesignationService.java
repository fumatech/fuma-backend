package com.backend.Service;

import java.util.List;
import com.backend.Entity.Designation;

public interface DesignationService {
	
	Designation saveDesignation(Designation designation);
	
	List<Designation> getAllDesignations();

	Designation updateDesignation(Long id, Designation designation);

	Designation getDesignationById(Long id);

	void deleteDesignationById(Long id);

}
