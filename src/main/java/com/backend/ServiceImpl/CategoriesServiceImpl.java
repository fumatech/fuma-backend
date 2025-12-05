package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Categories;
import com.backend.Repository.CategoriesRepo;
import com.backend.Service.CategoriesService;

import Exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepo catrepo;
     
    @Override
    public Categories saveCategories(Categories categories) {
        return catrepo.save(categories);
    }

    @Override
    public List<Categories> getAll() {
        return catrepo.findAll();
    }

    @Override
    public Optional<Categories> getById(Long id) {
        return catrepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        // Check if category exists before trying to delete
        if (catrepo.existsById(id)) {
            catrepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }
    
    @Transactional
    @Override
    public Categories updateCategory(Long categoryId, Categories updatedCategory) {
        Categories existingCategory = catrepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setCategoryCode(updatedCategory.getCategoryCode());
        existingCategory.setDescription(updatedCategory.getDescription());

        if (updatedCategory.getParentCategory() != null) {
            Categories parentCategory = catrepo.findById(updatedCategory.getParentCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Category not found"));
            existingCategory.setParentCategory(parentCategory);
        } else {
            existingCategory.setParentCategory(null);
        }

        Set<Categories> currentSubCategories = existingCategory.getSubCategories();
        for (Categories currentSubCategory : currentSubCategories) {
            if (!updatedCategory.getSubCategories().contains(currentSubCategory)) {
                currentSubCategory.setParentCategory(null);
                existingCategory.getSubCategories().remove(currentSubCategory);
                catrepo.delete(currentSubCategory);
            }
        }

        for (Categories newSubCategory : updatedCategory.getSubCategories()) {
            if (newSubCategory.getId() == null || !currentSubCategories.contains(newSubCategory)) {
                newSubCategory.setParentCategory(existingCategory);
                existingCategory.getSubCategories().add(newSubCategory);
            } else {
                Categories existingSubCategory = catrepo.findById(newSubCategory.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
                existingSubCategory.setCategoryName(newSubCategory.getCategoryName());
                existingSubCategory.setCategoryCode(newSubCategory.getCategoryCode());
                existingSubCategory.setDescription(newSubCategory.getDescription());
            }
        }

        return catrepo.save(existingCategory);
    }
}

