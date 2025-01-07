package com.grimpa.site.domain;


import com.grimpa.site.domain.dtos.FilePathDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FilePath implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String path;

    public FilePath() {
    }

    public FilePath(String id, String path, Pessoa pessoa) {
        this.id = id;
        this.path = path;
    }

    public FilePath(FilePathDto filePathDto) {
        this.id = filePathDto.id();
        this.path = filePathDto.path();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilePath filePath)) return false;
        return Objects.equals(getId(), filePath.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}

