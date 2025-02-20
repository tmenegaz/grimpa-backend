package com.grimpa.site.services;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.FilePath;
import com.grimpa.site.domain.Pessoa;
import com.grimpa.site.domain.dtos.ClienteDto;
import com.grimpa.site.domain.dtos.FilePathDto;
import com.grimpa.site.domain.enums.Excluido;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.repositories.FilePathRepository;
import com.grimpa.site.repositories.PessoaRepository;
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
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private FilePathRepository filePathRepository;

    public Cliente create(ClienteDto clienteDto) {
        clienteDto.setId(null);
        clienteDto.setSenha(encoder.encode(clienteDto.getSenha()));
        validaByCpfAndEmail(clienteDto);
        Cliente cliente = new Cliente(clienteDto);
        return repository.save(cliente);
    }

    @Transactional
    public Cliente update(String id, ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente clienteOld = this.findById(id);

        if (!clienteDto.getSenha().equals(clienteOld.getSenha())) {
            clienteDto.setSenha(encoder.encode(clienteDto.getSenha()));
        }

        validaByCpfAndEmail(clienteDto);
        clienteOld = new Cliente(clienteDto);
        return repository.save(clienteOld);
    }

    @Transactional
    public Cliente updateFilePath(String id, FilePathDto filePathDto) {
        Cliente clienteOld = this.findById(id);
        FilePath filePath = null;

        if (filePathDto.id() != null) {
            filePath = filePathRepository.findById(filePathDto.id())
                    .orElseThrow(
                            () -> new ObjectNotFoundException("Arquivo não encontrado")
                    );
        }
        clienteOld.setFilePath(filePath);
        return repository.save(clienteOld);
    }

    public Page<Cliente> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByExcluido(pageable);
    }

    public Cliente findById(String id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public List<Cliente> findAllByNome(String nome) {
        return repository.findAllByNome(nome);
    }

    public Cliente findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Cliente findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Cliente> findAllByPerfis(Integer perfil) {
        return repository.findAllByPerfis(perfil);
    }

    public List<Cliente> findAllByDataCriacao(String dataCriacao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(dataCriacao, formatter);
            return repository.findAllByDataCriacao(localDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido: " + dataCriacao, e);
        }
    }

    private void validaByCpfAndEmail(ClienteDto clienteDto) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDto.getCpf());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("Esse CPF já está cadastrado");
        }

        pessoa = pessoaRepository.findByEmail(clienteDto.getEmail());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("Esse e-mail já está cadastrado");
        }
    }

    public void delete(String id) {
        Cliente cliente = this.findById(id);
        if (!cliente.getProcessos().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui ordem de serviço e não pode ser deletado");
        }
        cliente.setExcluido(Excluido.EXCLUIDO);
        repository.save(cliente);
    }
}
