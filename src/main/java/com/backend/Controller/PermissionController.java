package com.backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.Entity.Permission;
import com.backend.Service.PermissionService;

@RestController
@RequestMapping("/permissions")
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
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	public PermissionController(PermissionService permissionService) {
		super();
		this.permissionService = permissionService;
	}

	public PermissionController() {
		super();
	}

	@PostMapping("/save")
	public ResponseEntity<Permission> savePermission(@RequestBody Permission permission) {
		Permission savedPermission = permissionService.savePermission(permission);
		return new ResponseEntity<>(savedPermission, HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Permission>> getAllPermissions() {
		List<Permission> permissions = permissionService.getAllPermissions();
		return new ResponseEntity<>(permissions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
		Permission permission = permissionService.getPermissionById(id);
		return new ResponseEntity<>(permission, HttpStatus.OK);
	}
}
