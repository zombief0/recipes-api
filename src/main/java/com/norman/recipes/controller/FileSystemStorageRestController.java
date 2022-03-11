package com.norman.recipes.controller;

import com.norman.recipes.service.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/file")
public class FileSystemStorageRestController {
    private final FileSystemStorageService fileSystemStorageService;

    @PostMapping
    public ResponseEntity<?> upload(
           @RequestPart MultipartFile file) throws IOException {
        return fileSystemStorageService.store(file);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestParam String filename) throws Exception {

        Resource file = fileSystemStorageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
