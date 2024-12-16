package com.grimpa.site.repositories;

import com.grimpa.site.domain.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Integer> {
}