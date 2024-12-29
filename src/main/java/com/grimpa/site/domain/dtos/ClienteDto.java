package com.grimpa.site.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.enums.Excluido;
import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;

    @NotNull(message = "O campo NOME é requerido")
    protected String nome;

    @NotNull(message = "O campo CPF é requerido")
    @CPF
    protected String cpf;

    @NotNull(message = "O campo E-MAIL é requerido")
    @Email
    protected String email;

    @NotNull(message = "O campo SENHA é requerido")
    protected String senha;

    private Excluido excluido;

    protected Set<Integer> perfis = new HashSet<>();

    protected Set<Integer> roles = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public ClienteDto() {
        super();
    }

    public ClienteDto(Cliente cliente) {
        super();
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
        this.perfis = cliente.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.roles = cliente.getRoles().stream().map(Roles::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = cliente.getDataCriacao();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public Set<Roles> getRoles() {
        return roles.stream().map(Roles::toEnum).collect(Collectors.toSet());
    }

    public void addRoles(Roles roles) {
        this.roles.add(roles.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
