package com.grimpa.site.resources;

import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.dtos.TecnicoDto;
import com.grimpa.site.services.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDto> create(@Valid @RequestBody TecnicoDto tecnicoDto) {
        Tecnico tecnico = service.create(tecnicoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> update(@PathVariable Integer id, @RequestBody TecnicoDto tecnicoDto ) {
        Tecnico tecnico = service.update(id, tecnicoDto);
        return ResponseEntity.ok().body(new TecnicoDto(tecnico));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> findById(@PathVariable Integer id) {
        Tecnico tecnico = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDto(tecnico));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDto>> findAll() {
        List<Tecnico> tecnicos = service.findAll();
        return ResponseEntity.ok().body(tecnicos.stream().map(TecnicoDto::new).collect(Collectors.toList()));
    }
}
