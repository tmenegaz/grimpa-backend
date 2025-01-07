package com.grimpa.site.services;

import com.grimpa.site.domain.FilePath;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.repositories.FilePathRepository;
import com.grimpa.site.services.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilePathService {

    private static final String PATH = "assets/conta/img/";

    @Autowired
    private FilePathRepository filePathRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public String uploadFile(MultipartFile file) {
        String uploadDir = System.getProperty("user.dir") + File.separator + "public" + File.separator + "assets" + File.separator + "conta" + File.separator + "img" + File.separator;
        File uploadDirectory = new File(uploadDir);

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename;
        File uploadFile = new File(uploadDir + file.getOriginalFilename());

        FilePath existingFilePath = filePathRepository.findByPath(PATH + fileName);
        if (existingFilePath != null) {
            return PATH + fileName;
        }

        try {
            file.transferTo(uploadFile);
        } catch (IOException io) {
            throw new FileStorageException(("O arquivo" + file.getOriginalFilename() + " não pode ser salvo"));
        }

        FilePath filePath = new FilePath();
        filePath.setPath(PATH + fileName);
        filePathRepository.save(filePath);

        return PATH + fileName;

    }

    public UrlResource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir") + File.separator + "public" + File.separator + "assets" + File.separator + "conta" + File.separator + "img" + File.separator).resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Arquivo não encontrado " + fileName);
            }
        } catch (Exception e) {
            throw new FileStorageException("Erro ao baixar o arquivo " + fileName, e);
        }
    }

    public void deleteFile(String fileName) {
        FilePath filePath = filePathRepository.findByPath(PATH + fileName);

        if (filePath != null) {
            File file = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "assets" + File.separator + "conta" + File.separator + "img" + File.separator + fileName);
            if (file.exists()) {
                file.delete();
                filePathRepository.delete(filePath);
            } else {
                throw new FileStorageException("Arquivo " + fileName + " não foi encontrado");
            }
        } else {
            throw new FileStorageException("Caminho do arquivo " + fileName + " não foi encontrado na base de dados");
        }
    }

    public FilePath findByPath(String fileName) {
        FilePath filePath = filePathRepository.findByPath(PATH + fileName);
        if (filePath != null) {
            return filePath;
        } else {
            throw new FileStorageException("Imagem não encontrada: " + fileName);
        }
    }
}



