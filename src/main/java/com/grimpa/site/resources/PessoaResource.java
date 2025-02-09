package com.grimpa.site.resources;

import com.grimpa.site.domain.dtos.PessoaDto;
import com.grimpa.site.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<PessoaDto> findPerfisByEmail(@PathVariable String email) {
        Set<Integer> perfil = service.findPerfisByEmail(email);
        return ResponseEntity.ok().body(new PessoaDto(perfil));
    }
}
