package com.grimpa.site.repositories;

import com.grimpa.site.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByEmail(String email);
}