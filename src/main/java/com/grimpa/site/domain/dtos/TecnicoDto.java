package com.grimpa.site.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grimpa.site.domain.FilePath;
import com.grimpa.site.domain.Tecnico;
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

public class TecnicoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo CPF é requerido")
    @CPF
    private String cpf;

    @NotNull(message = "O campo E-MAIL é requerido")
    @Email
    private String email;

    @NotNull(message = "O campo SENHA é requerido")
    private String senha;

    private FilePath filePath;

    private Set<Integer> perfis = new HashSet<>();

    private Set<Integer> roles = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;

    public TecnicoDto() {
        super();
    }

    public TecnicoDto(Tecnico tecnico) {
        super();
        this.id = tecnico.getId();
        this.nome = tecnico.getNome();
        this.cpf = tecnico.getCpf();
        this.email = tecnico.getEmail();
        this.senha = tecnico.getSenha();
        this.perfis = tecnico.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.roles = tecnico.getRoles().stream().map(Roles::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tecnico.getDataCriacao();
        this.filePath = tecnico.getFilePath();
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

    public FilePath getFilePath() {
        return filePath;
    }

    public void setFilePath(FilePath filePath) {
        this.filePath = filePath;
    }
}
