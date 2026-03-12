package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Vendor;

public interface VendorService {

    Vendor saveVendor(Vendor Vendor);

    boolean isActiveUser(String email);

    Optional<Vendor> findByEmail(String email);

    boolean authenticate(String email, String password);

    List<Vendor> getAllVendors();

    long getVendorCount();

    Vendor updateVendor(Long VendorId, Vendor updatedVendor);

    Vendor getVendorById(Long id);

    void deleteVendorById(Long id);

    // Add the following method to the interface
    Vendor getUserWithRolesAndPermissions(String email);

    Optional<String> getUserName(String email);

    Optional<String> findFirmNameByEmail(String email);

    Optional<Vendor> findByVendorId(String vendorId);

    Vendor toggleActiveStatus(Long vendorId, boolean isActive);

    List<Vendor> getAllActiveVendors();

    List<Vendor> getAllInactiveVendors();

}
