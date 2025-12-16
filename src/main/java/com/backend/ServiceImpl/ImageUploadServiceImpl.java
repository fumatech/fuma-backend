package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.UploadedImage;
import com.backend.Repository.UploadedImageRepository;
import com.backend.Service.ImageUploadService;
import com.backend.Util.FileUploadUtil;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@Autowired
	private UploadedImageRepository imageRepo;

	@Override
	public UploadedImage uploadImage(MultipartFile file) {
		try {
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String imagePath = fileUploadUtil.saveFile(fileName, file);

			// 🔥 Get existing record (only one)
			UploadedImage image;
			Optional<UploadedImage> existing = imageRepo.findAll().stream().findFirst();

			if (existing.isPresent()) {
				image = existing.get(); // UPDATE
			} else {
				image = new UploadedImage(); // INSERT first time
			}

			image.setImage(imagePath);
			return imageRepo.save(image);

		} catch (Exception e) {
			throw new RuntimeException("Image upload failed", e);
		}
	}

	@Override
	public List<UploadedImage> getAllImages() {
		return imageRepo.findAll();
	}
}
