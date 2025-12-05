package com.backend.Service;

import java.util.List;

import com.backend.Entity.Permission;

public interface PermissionService {

	List<Permission> getAllPermissions();

	Permission savePermission(Permission permission);

	Permission getPermissionById(Long id);

}
