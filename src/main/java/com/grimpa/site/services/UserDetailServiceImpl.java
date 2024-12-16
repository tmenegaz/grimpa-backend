package com.grimpa.site.services;

import com.grimpa.site.domain.Pessoa;
import com.grimpa.site.repositories.PessoaRepository;
import com.grimpa.site.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = repository.findByEmail(email);
        if (pessoa.isPresent())
            return new UserSS(
                    pessoa.get().getId(),
                    pessoa.get().getEmail(),
                    pessoa.get().getSenha(),
                    pessoa.get().getPerfis()
            );
        throw new UsernameNotFoundException(email);
    }
}
