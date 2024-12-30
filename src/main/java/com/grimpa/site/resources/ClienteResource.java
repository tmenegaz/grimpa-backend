package com.grimpa.site.resources;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.dtos.ClienteDto;
import com.grimpa.site.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.create(clienteDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> update(@PathVariable String id, @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.update(id, clienteDto);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable String id) {
        Cliente cliente = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Cliente> cliente = service.findAll(page, size);
        Page<ClienteDto> ClienteDto = cliente.map(ClienteDto::new);
        return ResponseEntity.ok().body(ClienteDto);
    }

    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<ClienteDto>> findAllByNome(@PathVariable String nome) {
        List<Cliente> clientes = service.findAllByNome(nome);
        return ResponseEntity.ok().body(clientes.stream().map(ClienteDto::new).collect(Collectors.toList()));
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<ClienteDto> findByCpf(@PathVariable String cpf) {
        Cliente cliente = service.findByCpf(cpf);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @GetMapping(value = "/perfis/{perfil}")
    public ResponseEntity<List<ClienteDto>> findAllByPerfil(@PathVariable Integer perfil) {
        List<Cliente> clientes = service.findAllByPerfis(perfil);
        return ResponseEntity.ok().body(clientes.stream().map(ClienteDto::new).collect(Collectors.toList()));
    }

    @GetMapping(value = "/data_criacao/{dataCriacao}")
    public ResponseEntity<List<ClienteDto>> findAllByDataCriacao(@PathVariable String dataCriacao) {
        List<Cliente> clientes = service.findAllByDataCriacao(dataCriacao);
        return ResponseEntity.ok().body(clientes.stream().map(ClienteDto::new).collect(Collectors.toList()));

    }


}
