package com.grimpa.site.repositories;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.grimpa.site.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}