package com.norman.recipes.service;


import com.norman.recipes.security.SecurityProperties;
import com.norman.recipes.service.dto.MessageResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

@Service
public class FileSystemStorageService {

    private final Path rootLocation;

    public FileSystemStorageService() {
        this.rootLocation = Paths.get(SecurityProperties.location);
    }

    public ResponseEntity<?> store(MultipartFile file) throws IOException {
        if (file != null) {
            init();
            String finalName = ZonedDateTime.now().toInstant().toEpochMilli() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(finalName));
            return ResponseEntity.ok(new MessageResponse(finalName));
        } else {
            return ResponseEntity.ok("");
        }
    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) throws FileNotFoundException, MalformedURLException {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Could not read file: " + filename);

        }
    }


    private void init() throws IOException {
        if (!Files.exists(this.rootLocation)) {
            Files.createDirectory(this.rootLocation);
        }
    }

}
