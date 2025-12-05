package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Vendor;

@Repository
public interface VendorRepo extends JpaRepository<Vendor, Long> {

	Optional<Vendor> findByEmail(String email);

	@Query("SELECT u.username FROM User u WHERE u.email = :email")
	Optional<String> getUsernameByEmail(String email);

	@Query("SELECT v.firmName FROM Vendor v WHERE v.email = :email")
	Optional<String> findFirmNameByEmail(String email);

	Optional<Vendor> findByVendorId(String vendorId);

	List<Vendor> findByIsActiveTrue();

	List<Vendor> findByIsActiveFalse();

}
