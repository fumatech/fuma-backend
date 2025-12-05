package com.backend.Repository;

import com.backend.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {
	List<Permission> findAllById(Iterable<Long> ids);
}
