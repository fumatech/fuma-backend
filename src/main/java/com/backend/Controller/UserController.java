package com.backend.Controller;

import java.util.List;
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

import com.backend.Entity.LoginRequest;
import com.backend.Entity.User;
import com.backend.Service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userservice;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userservice.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
        boolean isAuthenticated = userservice.authenticate(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            // Create or get the session
            session.setAttribute("userEmail", request.getEmail());
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials or account inactive", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestBody LoginRequest request, HttpSession session) {
        boolean isAuthenticated = userservice.authenticateAdmin(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            session.setAttribute("userEmail", request.getEmail());
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials or not an admin account", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/employee-login")
    public ResponseEntity<String> employeeLogin(@RequestBody LoginRequest request, HttpSession session) {
        boolean isAuthenticated = userservice.authenticateEmployee(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            session.setAttribute("userEmail", request.getEmail());
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials or not an employee account", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userservice.getallusers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(userservice.getUserCount());
    }

    @GetMapping("/getall-names")
    public ResponseEntity<List<java.util.Map<String, Object>>> getAllUserNames() {
        List<Object[]> results = userservice.getAllUserNames();
        List<java.util.Map<String, Object>> users = results.stream().map(row -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", row[0]);
            map.put("firstname", row[1]);
            map.put("lastname", row[2]);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userservice.findById(id).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userservice.updateUser(id, user).map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userservice.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserWithRolesAndPermissions(@PathVariable String email) {
        try {
            User user = userservice.getUserWithRolesAndPermissions(email);
            return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            // Handle multiple results error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        Optional<User> exists = userservice.findByEmail(email);

        if (exists.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Email exists");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist");
        }
    }

    @GetMapping("/username")
    public Optional<String> getUsername(@RequestParam String email) {
        return userservice.getUserName(email);
    }

}
