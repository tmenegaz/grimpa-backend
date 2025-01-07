package com.grimpa.site.resources;

import com.grimpa.site.domain.FilePath;
import com.grimpa.site.domain.dtos.FilePathDto;
import com.grimpa.site.services.FilePathService;
import com.grimpa.site.services.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class FilePathResource {

    @Autowired
    private FilePathService service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = service.uploadFile(file);
            return ResponseEntity.ok(fileName);
        } catch (FileStorageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao armazenar o arquivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido: " + e.getMessage());
        }
    }


    @GetMapping("/file/{fileName}")
    public ResponseEntity<UrlResource> getFile(@PathVariable String fileName) {
        UrlResource resource = service.loadFileAsResource(fileName);
        String contentType;
        try {
            Path path = Paths.get(resource.getURI());
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @DeleteMapping("/file/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            service.deleteFile(fileName);
            return ResponseEntity.noContent().build();
        } catch (FileStorageException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/filePath/{fileName}")
    public ResponseEntity<FilePathDto> findByPath(@PathVariable String fileName) {
        FilePath filePath = service.findByPath(fileName);
        return ResponseEntity.ok().body(new FilePathDto(filePath.getId(), filePath.getPath()));
    }
}



