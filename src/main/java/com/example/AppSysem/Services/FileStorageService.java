package com.example.AppSysem.Services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    @Value("${upload.directory}")
    private String uploadDirectory;

    public String storeFile(MultipartFile file) {
        String letter = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path filePath = Paths.get(uploadDirectory + letter);
            Files.copy(file.getInputStream(), filePath);
            return letter;
        } catch (IOException e) {
            // Handle the exception
        }

        return null;
    }
}
