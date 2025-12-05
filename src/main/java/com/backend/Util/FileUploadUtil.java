package com.backend.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // Create directory if it does not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        // Replace the file if it already exists
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return a path with forward slashes to ensure it works as a URL
        return "/uploads/" + fileName;
    }
}





