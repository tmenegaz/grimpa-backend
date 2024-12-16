package com.grimpa.site.resources;

import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.dtos.ClienteDto;
import com.grimpa.site.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "clientes")
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
    public ResponseEntity<ClienteDto> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto ) {
        Cliente cliente = service.update(id, clienteDto);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
        Cliente cliente = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<Cliente> clientes = service.findAll();
        return ResponseEntity.ok().body(clientes.stream().map(ClienteDto::new).collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
