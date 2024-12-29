package com.grimpa.site.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grimpa.site.domain.dtos.TecnicoDto;
import com.grimpa.site.domain.enums.Excluido;
import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Tecnico extends Pessoa {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<Processo> processos = new ArrayList<>();

    private Excluido excluido;

    public Tecnico() {
        super();
    }

    public Tecnico(String id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }

    public Tecnico(TecnicoDto tecnicoDto) {
        super();
        this.id = tecnicoDto.getId();
        this.nome = tecnicoDto.getNome();
        this.cpf = tecnicoDto.getCpf();
        this.email = tecnicoDto.getEmail();
        this.senha = tecnicoDto.getSenha();
        this.perfis = tecnicoDto.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.roles = tecnicoDto.getRoles().stream().map(Roles::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tecnicoDto.getDataCriacao();
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }

    public Tecnico toExcluidoTecnico(Integer excluido) {
        Tecnico tecnico = new Tecnico();
        try {
            tecnico.setExcluido(Excluido.toEnum(excluido));
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return tecnico;

    }

    public Excluido getExcluido() {
        return excluido;
    }

    public void setExcluido(Excluido excluido) {
        this.excluido = excluido;
    }
}
