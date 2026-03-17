package com.backend.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.backend.Entity.Permission;
import com.backend.Repository.PermissionRepo;

@Component
public class HRDocsPermissionSeeder implements CommandLineRunner {

    @Autowired
    private PermissionRepo permissionRepo;

    @Override
    public void run(String... args) {
        saveIfMissing("hr_docs.view");
        // saveIfMissing("hr_docs.manage");
    }

    private void saveIfMissing(String permissionName) {
        if (permissionRepo.findByName(permissionName) == null) {
            Permission permission = new Permission();
            permission.setName(permissionName);
            permissionRepo.save(permission);
        }
    }
}
