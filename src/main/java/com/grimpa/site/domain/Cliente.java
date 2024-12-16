package com.grimpa.site.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grimpa.site.domain.dtos.ClienteDto;
import com.grimpa.site.domain.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Cliente extends Pessoa {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Processo> processos = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(ClienteDto clienteDto) {
        super();
        this.id = clienteDto.getId();
        this.nome = clienteDto.getNome();
        this.cpf = clienteDto.getCpf();
        this.email = clienteDto.getEmail();
        this.senha = clienteDto.getSenha();
        this.perfis = clienteDto.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = clienteDto.getDataCriacao();
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }
}
