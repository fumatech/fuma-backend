package com.backend.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Customer;
import com.backend.Entity.DatabaseRequest;
import com.backend.Entity.LoginRequest;
import com.backend.Service.CustomerService;
import com.backend.ServiceImpl.DatabaseService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class CustomerController {

    @Autowired
    private CustomerService customerservice;

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        // Check if franchiseId already exists
        if (customerservice.existsByFranchiseId(customer.getFranchiseId())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Franchise ID already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Create database first
        DatabaseRequest dbRequest = new DatabaseRequest();
        dbRequest.setDbName(customer.getDbName());
        dbRequest.setDbUsername(customer.getDbUsername());
        dbRequest.setDbPassword(customer.getDbPassword());

        if (!databaseService.createDatabase(dbRequest)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to create database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // Save customer if database creation succeeded
        Customer savedCustomer = customerservice.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpSession session) {
        boolean isAuthenticated = customerservice.authenticate(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            Optional<Customer> customer = customerservice.findByEmailId(request.getEmail());
            if (customer.isPresent()) {
                session.setAttribute("userEmail", request.getEmail());
                session.setAttribute("tenantDbName", customer.get().getDbName());

                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("tenantDbName", customer.get().getDbName());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("message", "Invalid credentials or account inactive"),
                HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerservice.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCustomerCount() {
        return ResponseEntity.ok(customerservice.getCustomerCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customers = customerservice.getCustomerById(id);
        if (customers != null) {
            return ResponseEntity.ok(customers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/{id}")
    ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer custom = customerservice.updateCustomer(id, updatedCustomer);
        if (custom != null) {
            return ResponseEntity.ok(custom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerservice.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/franchiseName/email/{email}")
    public ResponseEntity<Map<String, String>> getFirmNameByEmail(@PathVariable String email) {
        Optional<String> franchiseName = customerservice.findFirmNameByEmail(email);
        if (franchiseName.isPresent()) {
            // Return a JSON object with a key "firmName" and the firm name value
            Map<String, String> response = new HashMap<>();
            response.put("franchiseName", franchiseName.get());
            return ResponseEntity.ok(response); // Return the response as a JSON object
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // In CustomerController.java
    @GetMapping("/checkFranchiseId/{franchiseId}")
    public ResponseEntity<Map<String, Boolean>> checkFranchiseIdExists(@PathVariable String franchiseId) {
        boolean exists = customerservice.existsByFranchiseId(franchiseId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("FranchiseId exists ", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmailId(@PathVariable String email) {
        Optional<Customer> customer = customerservice.findByEmailId(email);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = customerservice.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("emailExists", exists);
        return ResponseEntity.ok(response);
    }

    // Toggle activation status
    @PutMapping("/toggle-active/{id}")
    public ResponseEntity<Map<String, Object>> toggleCustomerActiveStatus(@PathVariable Long id,
            @RequestParam boolean isActive) {

        Customer updatedCustomer = customerservice.toggleActiveStatus(id, isActive);
        if (updatedCustomer != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Customer status updated");
            response.put("isActive", updatedCustomer.getIsActive());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Customer not found"));
        }
    }

    // Get all active customers
    @GetMapping("/getallactive")
    public ResponseEntity<List<Customer>> getAllActiveCustomers() {
        List<Customer> activeCustomers = customerservice.getAllActiveCustomers();
        return ResponseEntity.ok(activeCustomers);
    }

    // Get all inactive customers
    @GetMapping("/getallinactive")
    public ResponseEntity<List<Customer>> getAllInactiveCustomers() {
        List<Customer> inactiveCustomers = customerservice.getAllInactiveCustomers();
        return ResponseEntity.ok(inactiveCustomers);
    }

    @GetMapping("/franchise/{franchiseId}")
    public ResponseEntity<Customer> getCustomerByFranchiseId(@PathVariable String franchiseId) {
        Optional<Customer> customer = customerservice.findByFranchiseId(franchiseId);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<Customer> assignTags(@PathVariable Long id, @RequestBody java.util.List<Long> tagIds) {
        Customer updatedCustomer = customerservice.assignTags(id, tagIds);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<java.util.List<Customer>> getCustomersByTag(@RequestParam String tag) {
        return ResponseEntity.ok(customerservice.getCustomersByTag(tag));
    }

}
