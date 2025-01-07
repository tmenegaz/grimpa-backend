package com.grimpa.site.repositories;

import com.grimpa.site.domain.FilePath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePathRepository extends JpaRepository<FilePath, String> {
    FilePath findByPath(String path);
}
