package com.grimpa.site.repositories;

import com.grimpa.site.domain.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, String> {

    @Query("SELECT t FROM Tecnico t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Tecnico> findAllByNome(@Param("nome") String nome);

    Tecnico findByCpf(String cpf);

    @Query("SELECT c FROM Tecnico c JOIN Userss u ON c.email = u.username WHERE c.email = :email")
    Tecnico findByEmail(@Param("email") String email);

    List<Tecnico> findAllByPerfis(Integer perfil);

    @Query("SELECT t FROM Tecnico t WHERE t.dataCriacao = :dataCriacao")
    List<Tecnico> findAllByDataCriacao(@Param("dataCriacao") LocalDate dataCriacao);

    @Query("SELECT t FROM Tecnico t WHERE t.excluido = 0 or t.excluido is null")
    Page<Tecnico> findAllByExcluido(Pageable pageable);
}