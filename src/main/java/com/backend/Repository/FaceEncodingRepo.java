package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.FaceEncoding;

@Repository
public interface FaceEncodingRepo extends JpaRepository<FaceEncoding, Long> {

    Optional<FaceEncoding> findByEmployeeId(Long employeeId);

}
