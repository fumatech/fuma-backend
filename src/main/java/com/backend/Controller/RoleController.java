package com.backend.Controller;

import com.backend.Entity.Role;
import com.backend.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/role")
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
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/check")
	public ResponseEntity<?> checkRoleName(@RequestParam String name) {
		boolean exists = roleService.existsByRole(name);
		return new ResponseEntity<>(Map.of("exists", exists), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveRole(@RequestBody Role role) {
		// Debugging logs
	//	System.out.println("Received Role: " + role.getRole());
		//System.out.println("Received Permissions: "
		//		+ role.getPermissions().stream().map(permission -> permission.getId()).collect(Collectors.toList()));
		// Save the role
		Role savedRole = roleService.saveRole(role);
		return new ResponseEntity<>(savedRole, HttpStatus.CREATED);

	}

	@GetMapping("/getall")
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@GetMapping("/names")
	public ResponseEntity<List<Role>> getRolesWithId() {
		List<Role> roles = roleService.getRolesWithId();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
		try {
			Role role = roleService.updateRole(id, updatedRole);
			return new ResponseEntity<>(role, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
		try {
			roleService.deleteRoleById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
		try {
			Role role = roleService.getRoleById(id);
			return new ResponseEntity<>(role, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
