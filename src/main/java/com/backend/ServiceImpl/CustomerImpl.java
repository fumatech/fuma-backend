package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Customer;
import com.backend.Repository.CustomerRepo;
import com.backend.Service.CustomerService;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerrepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        // TODO Auto-generated method stub
        return customerrepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        // TODO Auto-generated method stub
        return customerrepo.findAll();
    }

    @Override
    public long getCustomerCount() {
        return customerrepo.count();
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerrepo.findById(customerId);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();

            existingCustomer.setPrefix(updatedCustomer.getPrefix());
            existingCustomer.setFirstname(updatedCustomer.getFirstname());
            existingCustomer.setLastname(updatedCustomer.getLastname());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setFranchiseId(updatedCustomer.getFranchiseId());
            existingCustomer.setFranchiseName(updatedCustomer.getFranchiseName());
            existingCustomer.setTaxOrGstNumber(updatedCustomer.getTaxOrGstNumber());
            existingCustomer.setShopActNumber(updatedCustomer.getShopActNumber());
            existingCustomer.setCinNumber(updatedCustomer.getCinNumber());
            existingCustomer.setPanNumber(updatedCustomer.getPanNumber());
            existingCustomer.setIsActive(updatedCustomer.getIsActive());
            existingCustomer.setUsername(updatedCustomer.getUsername());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setAllowLogin(updatedCustomer.getAllowLogin());
            existingCustomer.setLanguage(updatedCustomer.getLanguage());
            existingCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
            existingCustomer.setGender(updatedCustomer.getGender());
            existingCustomer.setMaritalStatus(updatedCustomer.getMaritalStatus());
            existingCustomer.setBloodGroup(updatedCustomer.getBloodGroup());
            existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
            existingCustomer.setAlternateContactNumber(updatedCustomer.getAlternateContactNumber());
            existingCustomer.setFamilyContactNumber(updatedCustomer.getFamilyContactNumber());
            existingCustomer.setFacebookLink(updatedCustomer.getFacebookLink());
            existingCustomer.setTwitterLink(updatedCustomer.getTwitterLink());
            existingCustomer.setSocialMedia1(updatedCustomer.getSocialMedia1());
            existingCustomer.setSocialMedia2(updatedCustomer.getSocialMedia2());
            existingCustomer.setCustomField1(updatedCustomer.getCustomField1());
            existingCustomer.setCustomField2(updatedCustomer.getCustomField2());
            existingCustomer.setCustomField3(updatedCustomer.getCustomField3());
            existingCustomer.setCustomField4(updatedCustomer.getCustomField4());
            existingCustomer.setGuardianName(updatedCustomer.getGuardianName());
            existingCustomer.setIdProofName(updatedCustomer.getIdProofName());
            existingCustomer.setIdProofNumber(updatedCustomer.getIdProofNumber());
            existingCustomer.setPermanentAddress(updatedCustomer.getPermanentAddress());
            existingCustomer.setCurrentAddress(updatedCustomer.getCurrentAddress());
            existingCustomer.setCountry(updatedCustomer.getCountry());
            existingCustomer.setState(updatedCustomer.getState());
            existingCustomer.setCity(updatedCustomer.getCity());
            existingCustomer.setZipCode(updatedCustomer.getZipCode());
            existingCustomer.setAccountHolderName(updatedCustomer.getAccountHolderName());
            existingCustomer.setAccountNumber(updatedCustomer.getAccountNumber());
            existingCustomer.setBankName(updatedCustomer.getBankName());
            existingCustomer.setIfsc(updatedCustomer.getIfsc());
            existingCustomer.setBranch(updatedCustomer.getBranch());
            existingCustomer.setTaxPayerId(updatedCustomer.getTaxPayerId());

            return customerrepo.save(existingCustomer);
        } else {
            return null;
        }
    }

    @Override
    public Customer getCustomerById(Long id) {
        // TODO Auto-generated method stub

        Optional<Customer> customers = customerrepo.findById(id);
        return customers.orElse(null);
    }

    @Override
    public void deleteCustomerById(Long id) {
        // TODO Auto-generated method stub

        customerrepo.deleteById(id);

    }

    @Override
    public boolean isActiveUser(String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        // TODO Auto-generated method stub
        return customerrepo.findByEmail(email);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<Customer> userOpt = customerrepo.findByEmail(email);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password) && userOpt.get().getIsActive();
    }

    @Override
    public Optional<String> getUserName(String email) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<String> findFirmNameByEmail(String email) {
        // TODO Auto-generated method stub
        return customerrepo.findFirmNameByEmail(email);
    }

    @Override
    public boolean existsByFranchiseId(String franchiseId) {
        return customerrepo.existsByFranchiseId(franchiseId);
    }

    @Override
    public Optional<Customer> findByEmailId(String email) {
        // TODO Auto-generated method stub
        return customerrepo.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerrepo.findByEmail(email).isPresent();
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return customerrepo.findByIsActiveTrue();
    }

    @Override
    public List<Customer> getAllInactiveCustomers() {
        return customerrepo.findByIsActiveFalse();
    }

    @Override
    public Customer toggleActiveStatus(Long id, boolean isActive) {
        Optional<Customer> optionalCustomer = customerrepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setIsActive(isActive);
            return customerrepo.save(customer);
        }
        return null;
    }

    @Override
    public Optional<Customer> findByFranchiseId(String franchiseId) {
        return customerrepo.findByFranchiseId(franchiseId);
    }

}
