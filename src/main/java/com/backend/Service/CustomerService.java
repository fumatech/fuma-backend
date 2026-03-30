package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    long getCustomerCount();

    Customer updateCustomer(Long CustomerId, Customer updatedCustomer);

    Customer getCustomerById(Long id);

    void deleteCustomerById(Long id);

    boolean isActiveUser(String email);

    Optional<Customer> findByEmail(String email);

    boolean authenticate(String email, String password);

    Optional<String> getUserName(String email);

    Optional<String> findFirmNameByEmail(String email);

    boolean existsByFranchiseId(String franchiseId);

    Optional<Customer> findByEmailId(String email);

    boolean existsByEmail(String email);

    List<Customer> getAllActiveCustomers();

    List<Customer> getAllInactiveCustomers();

    Customer toggleActiveStatus(Long id, boolean isActive);

    Optional<Customer> findByFranchiseId(String franchiseId);

    Customer assignTags(Long customerId, java.util.List<Long> tagIds);

    java.util.List<Customer> getCustomersByTag(String tagName);
}
