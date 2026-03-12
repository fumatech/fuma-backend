package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.User;

public interface UserService {

    User saveUser(User user);

    boolean isActiveUser(String email);

    Optional<User> findByEmail(String email);

    boolean authenticate(String email, String password);

    boolean authenticateAdmin(String email, String password);

    boolean authenticateEmployee(String email, String password);

    List<User> getallusers();

    long getUserCount();

    Optional<User> findById(Long id);

    Optional<User> updateUser(Long id, User user);

    boolean deleteUser(Long id);

    // Add the following method to the interface
    User getUserWithRolesAndPermissions(String email);

    Optional<String> getUserName(String email);

    List<Object[]> getAllUserNames();
}
