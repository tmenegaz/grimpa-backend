package com.grimpa.site.repositories;

import com.grimpa.site.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Query("SELECT t FROM Tecnico t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Tecnico> findByNome(@Param("nome") String nome);

    Tecnico findByCpf(String cpf);

    List<Tecnico> findByPerfis(Integer perfil);


    @Query("SELECT t FROM Tecnico t WHERE t.dataCriacao = :dataCriacao")
    List<Tecnico> findByDataCriacao(@Param("dataCriacao") LocalDate dataCriacao);
}