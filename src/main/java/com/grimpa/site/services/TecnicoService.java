package com.grimpa.site.services;

import com.grimpa.site.domain.Pessoa;
import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.dtos.TecnicoDto;
import com.grimpa.site.domain.enums.Excluido;
import com.grimpa.site.repositories.PessoaRepository;
import com.grimpa.site.repositories.TecnicoRepository;
import com.grimpa.site.services.exceptions.DataIntegrityViolationException;
import com.grimpa.site.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico create(TecnicoDto tecnicoDto) {
        tecnicoDto.setId(null);
        tecnicoDto.setSenha(encoder.encode(tecnicoDto.getSenha()));
        validaByCpfAndEmail(tecnicoDto);
        Tecnico tecnico = new Tecnico(tecnicoDto);
        return repository.save(tecnico);
    }

    @Transactional
    public Tecnico update(String id, TecnicoDto tecnicoDto) {
        tecnicoDto.setId(id);
        Tecnico tecnicoOld = this.findById(id);

        if (!tecnicoDto.getSenha().equals(tecnicoOld.getSenha())) {
            tecnicoDto.setSenha(encoder.encode(tecnicoDto.getSenha()));
        }

        validaByCpfAndEmail(tecnicoDto);
        tecnicoOld = new Tecnico(tecnicoDto);
        return repository.save(tecnicoOld);
    }

    public Page<Tecnico> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByExcluido(pageable);
    }

    public Tecnico findById(String id) {
        Optional<Tecnico> tecnico = repository.findById(id);
        return tecnico.orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado"));
    }

    public List<Tecnico> findAllByNome(String nome) {
        return repository.findAllByNome(nome);
    }

    public Tecnico findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public List<Tecnico> findAllByPerfis(Integer perfil) {
        return repository.findAllByPerfis(perfil);
    }

    public List<Tecnico> findAllByDataCriacao(String dataCriacao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(dataCriacao, formatter);
            return repository.findAllByDataCriacao(localDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido: " + dataCriacao, e);
        }
    }

    private void validaByCpfAndEmail(TecnicoDto tecnicoDto) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDto.getCpf());
        if (pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), tecnicoDto.getId())) {
            throw new DataIntegrityViolationException("Esse CPF já está cadastrado");
        }

        pessoa = pessoaRepository.findByEmail(tecnicoDto.getEmail());
        if (pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), tecnicoDto.getId())) {
            throw new DataIntegrityViolationException("Esse e-mail já está cadastrado");
        }
    }

    public void delete(String id) {
        Tecnico tecnico = this.findById(id);
        if (!tecnico.getProcessos().isEmpty()) {
            throw new DataIntegrityViolationException("Técnico possui ordem de serviço e não pode ser deletado");
        }
        tecnico.setExcluido(Excluido.EXCLUIDO);
        repository.save(tecnico);
    }
}
