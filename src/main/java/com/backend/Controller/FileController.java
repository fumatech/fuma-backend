package com.backend.Controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource; // Correct import for Resource
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Correct import for MediaType
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.UploadedImage;
import com.backend.Service.ImageUploadService;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class FileController {

	private final ResourceLoader resourceLoader;

	@Value("${file.upload-dir}")
	private String uploadDir; // Inject the upload directory

	public FileController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Autowired
	private ImageUploadService imageUploadService;

	@PostMapping("/upload")
	public ResponseEntity<UploadedImage> uploadImage(@RequestParam("file") MultipartFile file) {

		return ResponseEntity.ok(imageUploadService.uploadImage(file));
	}

	@GetMapping("/get-all")
	public ResponseEntity<List<UploadedImage>> getAllFiles() {
		return ResponseEntity.ok(imageUploadService.getAllImages());
	}

	@GetMapping("/uploads/{filename}")
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Path file = Paths.get(uploadDir).resolve(filename); // Use the injected uploadDir
		Resource resource;

		try {
			resource = new UrlResource(file.toUri());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		// Check if the file exists and return 404 if not found
		if (!resource.exists() || !resource.isReadable()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // Set the correct content type
				.body(resource);
	}

}
