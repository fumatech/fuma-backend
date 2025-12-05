package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Categories;

public interface CategoriesService {
    // Save or update a category
    Categories saveCategories(Categories categories);

    // Retrieve all categories
    List<Categories> getAll();

    // Retrieve a category by its ID
    Optional<Categories> getById(Long id);

    // Delete a category by its ID
    void deleteById(Long id);

    // Update a category by its ID
    Categories updateCategory(Long categoryId, Categories updatedCategory);
}
