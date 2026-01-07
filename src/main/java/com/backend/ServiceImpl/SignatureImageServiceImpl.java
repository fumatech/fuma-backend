package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.SignatureImage;
import com.backend.Repository.SignatureImageRepository;
import com.backend.Service.SignatureImageService;
import com.backend.Util.FileUploadUtil;

@Service
public class SignatureImageServiceImpl implements SignatureImageService {

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@Autowired
	private SignatureImageRepository imageRepo;

	@Override
	public SignatureImage uploadImage(MultipartFile file) {
		try {
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String imagePath = fileUploadUtil.saveFile(fileName, file);

			// 🔥 Get existing record (only one)
			SignatureImage image;
			Optional<SignatureImage> existing = imageRepo.findAll().stream().findFirst();

			if (existing.isPresent()) {
				image = existing.get(); // UPDATE
			} else {
				image = new SignatureImage(); // INSERT first time
			}

			image.setImage(imagePath);
			return imageRepo.save(image);

		} catch (Exception e) {
			throw new RuntimeException("Image upload failed", e);
		}
	}

	@Override
	public List<SignatureImage> getAllImages() {
		return imageRepo.findAll();
	}

}
