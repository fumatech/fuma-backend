package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Designation;

@Repository
public interface DesignationRepo extends JpaRepository<Designation, Long> {

}
