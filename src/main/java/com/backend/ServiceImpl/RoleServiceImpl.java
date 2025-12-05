package com.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.Entity.Permission;
import com.backend.Entity.Role;
import com.backend.Repository.RoleRepository;
import com.backend.Repository.PermissionRepo;
import com.backend.Service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepo permissionRepository;

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role updateRole(Long roleId, Role updatedRole) {
		return roleRepository.findById(roleId).map(role -> {
			role.setRole(updatedRole.getRole());

			// Handle permissions update
			Set<Long> permissionIds = updatedRole.getPermissions().stream().map(Permission::getId)
					.collect(Collectors.toSet());

			Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(permissionIds));
			role.setPermissions(permissions);

			return roleRepository.save(role);
		}).orElseThrow(() -> new RuntimeException("Role not found with id " + roleId));
	}

	@Override
	public Role getRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with id " + id));
	}

	@Override
	public List<Role> getRolesWithId() {
		return roleRepository.findAll();
	}

	@Override
	public void deleteRoleById(Long id) {
		roleRepository.findById(id).map(role -> {
			roleRepository.delete(role);
			return role;
		}).orElseThrow(() -> new RuntimeException("Role not found with id " + id));
	}

	@Override
	public boolean existsByRole(String roleName) {
		return roleRepository.existsByRole(roleName);
	}
}
