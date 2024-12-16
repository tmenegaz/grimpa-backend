package com.grimpa.site.services;

import com.grimpa.site.domain.Pessoa;
import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.dtos.ClienteDto;
import com.grimpa.site.repositories.PessoaRepository;
import com.grimpa.site.repositories.ClienteRepository;
import com.grimpa.site.services.exceptions.DataIntegrityViolationException;
import com.grimpa.site.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Cliente create(ClienteDto clienteDto) {
        clienteDto.setId(null);
        clienteDto.setSenha(encoder.encode(clienteDto.getSenha()));
        validaByCpfAndEmail(clienteDto);
        Cliente cliente = new Cliente(clienteDto);
        return repository.save(cliente);
    }

    @Transactional
    public Cliente update(Integer id, ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente clienteOld = this.findById(id);

        validaByCpfAndEmail(clienteDto);
        clienteOld = new Cliente(clienteDto);
        return repository.save(clienteOld);
    }

    public List<Cliente> findAll() {
        List<Cliente> clientes = repository.findAll();
        return clientes;
    }

    public Cliente findById(Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
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

    public void delete(Integer id) {
        Cliente cliente = this.findById(id);
        if (!cliente.getProcessos().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui ordem de serviço e não pode ser deletado");
        }
        repository.deleteById(id);
    }
}
