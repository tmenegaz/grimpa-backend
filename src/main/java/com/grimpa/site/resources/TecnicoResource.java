package com.grimpa.site.resources;

import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.dtos.TecnicoDto;
import com.grimpa.site.services.TecnicoService;
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
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @PostMapping
    public ResponseEntity<TecnicoDto> create(@Valid @RequestBody TecnicoDto tecnicoDto) {
        Tecnico tecnico = service.create(tecnicoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> update(@PathVariable String id, @RequestBody TecnicoDto tecnicoDto) {
        Tecnico tecnico = service.update(id, tecnicoDto);
        return ResponseEntity.ok().body(new TecnicoDto(tecnico));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDto> findById(@PathVariable String id) {
        Tecnico tecnico = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDto(tecnico));
    }

    @GetMapping
    public ResponseEntity<Page<TecnicoDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Tecnico> tecnicos = service.findAll(page, size);
        Page<TecnicoDto> tecnicoDtos = tecnicos.map(TecnicoDto::new);
        return ResponseEntity.ok().body(tecnicoDtos);
    }

    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<TecnicoDto>> findAllByNome(@PathVariable String nome) {
        List<Tecnico> tecnicos = service.findAllByNome(nome);
        return ResponseEntity.ok().body(tecnicos.stream().map(TecnicoDto::new).collect(Collectors.toList()));
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<TecnicoDto> findByCpf(@PathVariable String cpf) {
        Tecnico tecnico = service.findByCpf(cpf);
        return ResponseEntity.ok().body(new TecnicoDto(tecnico));
    }

    @GetMapping(value = "/perfis/{perfil}")
    public ResponseEntity<List<TecnicoDto>> findAllByPerfil(@PathVariable Integer perfil) {
        List<Tecnico> tecnicos = service.findAllByPerfis(perfil);
        return ResponseEntity.ok().body(tecnicos.stream().map(TecnicoDto::new).collect(Collectors.toList()));
    }

    @GetMapping(value = "/data_criacao/{dataCriacao}")
    public ResponseEntity<List<TecnicoDto>> findAllByDataCriacao(@PathVariable String dataCriacao) {
        List<Tecnico> tecnicos = service.findAllByDataCriacao(dataCriacao);
        return ResponseEntity.ok().body(tecnicos.stream().map(TecnicoDto::new).collect(Collectors.toList()));

    }
}
