package com.grimpa.site.repositories;

import com.grimpa.site.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByEmail(String email);

    @Query("SELECT p.perfis FROM Pessoa p JOIN Userss u ON p.email = u.username WHERE p.email = :email")
    Set<Integer> findPerfisByEmail(@Param("email") String email);
}