package com.backend.Service;

import com.backend.Entity.Role;
import java.util.List;

public interface RoleService {
	Role saveRole(Role role);

	List<Role> getAllRoles();

	Role updateRole(Long roleId, Role updatedRole);

	Role getRoleById(Long id);

	List<Role> getRolesWithId();

	void deleteRoleById(Long id);

	boolean existsByRole(String roleName);
}
