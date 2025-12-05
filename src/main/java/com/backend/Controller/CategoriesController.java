package com.backend.Controller;
import com.backend.Entity.Categories;
import com.backend.Service.CategoriesService;

import Exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/save")
    public ResponseEntity<Categories> createCategory(@RequestBody Categories categories) {
        try {
            Categories savedCategory = categoriesService.saveCategories(categories);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categoriesList = categoriesService.getAll();
        return new ResponseEntity<>(categoriesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable("id") Long id) {
        Optional<Categories> category = categoriesService.getById(id);
        return category.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                       .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable("id") Long id, @RequestBody Categories updatedCategory) {
        try {
            Categories savedCategory = categoriesService.updateCategory(id, updatedCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoriesService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
