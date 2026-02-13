package com.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Permission;
import com.backend.Repository.PermissionRepo;
import com.backend.Service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepo permissionRepo;

	@Override
	public List<Permission> getAllPermissions() {
		return permissionRepo.findAll();
	}

	@Override
	public Permission savePermission(Permission permission) {
		Permission existingPermission = permissionRepo.findByName(permission.getName());
		if (existingPermission != null) {
			return existingPermission;
		}
		return permissionRepo.save(permission);
	}

	@Override
	public Permission getPermissionById(Long id) {
		return permissionRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Permission not found with id " + id));
	}
}
