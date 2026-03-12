package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Vendor;
import com.backend.Repository.VendorRepo;
import com.backend.Service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepo vendorrepo;

    @Override
    public Vendor saveVendor(Vendor vendor) {
        // Save the vendor to the database
        return vendorrepo.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        // Retrieve all vendors from the database
        return vendorrepo.findAll();
    }

    @Override
    public long getVendorCount() {
        return vendorrepo.count();
    }

    @Override
    public Vendor updateVendor(Long vendorId, Vendor updatedVendor) {
        Optional<Vendor> existingVendorOptional = vendorrepo.findById(vendorId);

        if (existingVendorOptional.isPresent()) {
            Vendor existingVendor = existingVendorOptional.get();

            existingVendor.setPrefix(updatedVendor.getPrefix());
            existingVendor.setFirstname(updatedVendor.getFirstname());
            existingVendor.setLastname(updatedVendor.getLastname());
            existingVendor.setEmail(updatedVendor.getEmail());
            existingVendor.setVendorId(updatedVendor.getVendorId());
            existingVendor.setFirmName(updatedVendor.getFirmName());
            existingVendor.setTaxOrGstNumber(updatedVendor.getTaxOrGstNumber());
            existingVendor.setShopActNumber(updatedVendor.getShopActNumber());
            existingVendor.setCinNumber(updatedVendor.getCinNumber());
            existingVendor.setPanNumber(updatedVendor.getPanNumber());
            existingVendor.setIsActive(updatedVendor.getIsActive());
            existingVendor.setUsername(updatedVendor.getUsername());
            existingVendor.setPassword(updatedVendor.getPassword());
            existingVendor.setLanguage(updatedVendor.getLanguage());
            existingVendor.setDateOfBirth(updatedVendor.getDateOfBirth());
            existingVendor.setGender(updatedVendor.getGender());
            existingVendor.setMaritalStatus(updatedVendor.getMaritalStatus());
            existingVendor.setBloodGroup(updatedVendor.getBloodGroup());
            existingVendor.setMobileNumber(updatedVendor.getMobileNumber());
            existingVendor.setAlternateContactNumber(updatedVendor.getAlternateContactNumber());
            existingVendor.setFamilyContactNumber(updatedVendor.getFamilyContactNumber());
            existingVendor.setFacebookLink(updatedVendor.getFacebookLink());
            existingVendor.setTwitterLink(updatedVendor.getTwitterLink());
            existingVendor.setSocialMedia1(updatedVendor.getSocialMedia1());
            existingVendor.setSocialMedia2(updatedVendor.getSocialMedia2());
            existingVendor.setCustomField1(updatedVendor.getCustomField1());
            existingVendor.setCustomField2(updatedVendor.getCustomField2());
            existingVendor.setCustomField3(updatedVendor.getCustomField3());
            existingVendor.setCustomField4(updatedVendor.getCustomField4());
            existingVendor.setGuardianName(updatedVendor.getGuardianName());
            existingVendor.setIdProofName(updatedVendor.getIdProofName());
            existingVendor.setIdProofNumber(updatedVendor.getIdProofNumber());
            existingVendor.setPermanentAddress(updatedVendor.getPermanentAddress());
            existingVendor.setCurrentAddress(updatedVendor.getCurrentAddress());
            existingVendor.setCountry(updatedVendor.getCountry());
            existingVendor.setState(updatedVendor.getState());
            existingVendor.setCity(updatedVendor.getCity());
            existingVendor.setZipCode(updatedVendor.getZipCode());
            existingVendor.setAccountHolderName(updatedVendor.getAccountHolderName());
            existingVendor.setAccountNumber(updatedVendor.getAccountNumber());
            existingVendor.setBankName(updatedVendor.getBankName());
            existingVendor.setIfsc(updatedVendor.getIfsc());
            existingVendor.setBranch(updatedVendor.getBranch());
            existingVendor.setTaxPayerId(updatedVendor.getTaxPayerId());
            return vendorrepo.save(existingVendor);
        } else {
            return null;
        }
    }

    @Override
    public Vendor getVendorById(Long id) {
        // Retrieve a vendor by its ID
        Optional<Vendor> vendorOptional = vendorrepo.findById(id);
        return vendorOptional.orElse(null); // Return null if not found
    }

    @Override
    public void deleteVendorById(Long id) {
        // Delete the vendor from the database by ID
        vendorrepo.deleteById(id);
    }

    @Override
    public boolean isActiveUser(String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Vendor> findByEmail(String email) {
        return vendorrepo.findByEmail(email);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<Vendor> userOpt = vendorrepo.findByEmail(email);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password)
                && Boolean.TRUE.equals(userOpt.get().getIsActive()); // Only allow if active
    }

    @Override
    public Vendor getUserWithRolesAndPermissions(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<String> getUserName(String email) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<String> findFirmNameByEmail(String email) {
        // TODO Auto-generated method stub
        return vendorrepo.findFirmNameByEmail(email);
    }

    @Override
    public Optional<Vendor> findByVendorId(String vendorId) {
        return vendorrepo.findByVendorId(vendorId);
    }

    @Override
    public Vendor toggleActiveStatus(Long vendorId, boolean isActive) {
        Optional<Vendor> vendorOpt = vendorrepo.findById(vendorId);
        if (vendorOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            vendor.setIsActive(isActive);
            return vendorrepo.save(vendor);
        }
        return null;
    }

    @Override
    public List<Vendor> getAllActiveVendors() {
        return vendorrepo.findByIsActiveTrue(); // This method we'll define in the repo
    }

    @Override
    public List<Vendor> getAllInactiveVendors() {
        return vendorrepo.findByIsActiveFalse(); // This repo method will be created next
    }

}
