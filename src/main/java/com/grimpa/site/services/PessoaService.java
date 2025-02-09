package com.grimpa.site.services;

import com.grimpa.site.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;


    public Set<Integer> findPerfisByEmail(String email) {
        return repository.findPerfisByEmail(email);
    }

}
