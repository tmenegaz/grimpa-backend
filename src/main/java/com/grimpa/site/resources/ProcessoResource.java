package com.grimpa.site.resources;

import com.grimpa.site.domain.Processo;
import com.grimpa.site.domain.dtos.ProcessoDto;
import com.grimpa.site.services.ProcessoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "processos")
public class ProcessoResource {

    @Autowired
    private ProcessoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProcessoDto> findById(@PathVariable Integer id) {
        Processo processo = service.findById(id);
        return ResponseEntity.ok().body(new ProcessoDto(processo));
    }

    @GetMapping
    public ResponseEntity<List<ProcessoDto>> findAll() {
        List<Processo> processos = service.findAll();
        return ResponseEntity.ok().body(processos.stream().map(ProcessoDto::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ProcessoDto> create(@RequestBody ProcessoDto processoDto) {
        Processo processo = service.create(processoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(processo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProcessoDto> update(@PathVariable Integer id, @RequestBody ProcessoDto processoDto) {
        Processo processo = service.update(id, processoDto);
        return ResponseEntity.ok().body(new ProcessoDto(processo));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProcessoDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
