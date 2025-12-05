package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Long> {

}
