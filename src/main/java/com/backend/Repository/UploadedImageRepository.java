package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.UploadedImage;

@Repository
public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {
}
