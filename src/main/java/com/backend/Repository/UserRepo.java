package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions WHERE u.email = :email")
    List<User> findByEmailItsPermissions(String email);

    @Query("SELECT u.username FROM User u WHERE u.email = :email")
    Optional<String> getUsernameByEmail(String email);

    @Query("SELECT u.id, u.firstname, u.lastname FROM User u")
    List<Object[]> findAllIdAndName();

    long countByIsActive(Boolean isActive);

    @Query("SELECT u.departmentId, COUNT(u) FROM User u WHERE u.departmentId IS NOT NULL GROUP BY u.departmentId")
    List<Object[]> countByDepartmentGroup();

}
