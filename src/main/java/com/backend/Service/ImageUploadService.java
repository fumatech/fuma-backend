package com.backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.UploadedImage;

public interface ImageUploadService {
	UploadedImage uploadImage(MultipartFile file);

	List<UploadedImage> getAllImages(); // 👈 ADD

}
