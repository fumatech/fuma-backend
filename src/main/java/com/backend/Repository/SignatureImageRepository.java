package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.SignatureImage;

@Repository
public interface SignatureImageRepository extends JpaRepository<SignatureImage, Long> {

}
