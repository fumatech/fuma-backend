package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.BusinessCategory;
import com.backend.Service.BusinessCategoryService;

@RestController
@RequestMapping("/business-category")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class BusinessCategoryController {

	@Autowired
	private BusinessCategoryService service;

	// ✅ SAVE
	@PostMapping("/save")
	public BusinessCategory save(@RequestBody BusinessCategory category) {
		return service.save(category);
	}

	// ✅ GET ALL
	@GetMapping("/getall")
	public List<BusinessCategory> getAll() {
		return service.getAll();
	}

	// ✅ GET BY ID
	@GetMapping("/get/{id}")
	public BusinessCategory getById(@PathVariable Long id) {
		return service.getById(id);
	}

	// ✅ UPDATE
	@PutMapping("/update/{id}")
	public BusinessCategory update(@PathVariable Long id, @RequestBody BusinessCategory category) {
		return service.update(id, category);
	}

	// ✅ DELETE
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "BusinessCategory deleted successfully";
	}
}
