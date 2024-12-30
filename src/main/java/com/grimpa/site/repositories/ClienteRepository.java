package com.grimpa.site.repositories;

import com.grimpa.site.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> findAllByNome(@Param("nome") String nome);

    Cliente findByCpf(String cpf);

    List<Cliente> findAllByPerfis(Integer perfil);

    @Query("SELECT c FROM Cliente c WHERE c.dataCriacao = :dataCriacao")
    List<Cliente> findAllByDataCriacao(@Param("dataCriacao") LocalDate dataCriacao);

    @Query("SELECT c FROM Cliente c WHERE c.excluido = 0 or c.excluido is null")
    Page<Cliente> findAllByExcluido(Pageable pageable);
}