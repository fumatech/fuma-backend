package com.backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.SignatureImage;

public interface SignatureImageService {
	SignatureImage uploadImage(MultipartFile file);

	List<SignatureImage> getAllImages();
}
