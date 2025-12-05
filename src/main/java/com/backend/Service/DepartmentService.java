package com.backend.Service;

import java.util.List;
import com.backend.Entity.Department;

public interface DepartmentService {
	
	
	Department saveDepartment(Department department);
	
	List<Department> getAllDepartments();

	Department updateDepartment(Long id, Department department);

	Department getDepartmentById(Long id);

	void deleteDepartmentById(Long id);

}
