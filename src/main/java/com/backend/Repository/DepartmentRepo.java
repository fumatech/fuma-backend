package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
	
    boolean existsByDepartmentId(Long departmentId);


}
