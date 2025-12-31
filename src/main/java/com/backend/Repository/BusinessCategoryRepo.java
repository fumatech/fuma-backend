package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.BusinessCategory;

@Repository
public interface BusinessCategoryRepo extends JpaRepository<BusinessCategory, Long> {

}
