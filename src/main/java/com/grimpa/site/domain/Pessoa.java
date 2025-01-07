package com.grimpa.site.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Roles;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public abstract class Pessoa implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;
    protected String nome;

    @Column(unique = true)
    protected String cpf;

    @Column(unique = true)
    protected String email;

    protected String senha;

    @OneToOne
    @JoinColumn(name = "file_path_id")
    protected FilePath filePath;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    protected Set<Integer> roles = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao;

    public Pessoa() {
        super();
    }

    public Pessoa(String id, String nome, String cpf, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
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
        return this.perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public Perfil getPerfil() {
        return this.perfis.stream().map(Perfil::toEnum).findFirst().orElse(null);
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public Set<Roles> getRoles() {
        return this.roles.stream().map(Roles::toEnum).collect(Collectors.toSet());
    }

    public Roles getRole() {
        return this.roles.stream().map(Roles::toEnum).findFirst().orElse(null);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa pessoa)) return false;
        return Objects.equals(getId(), pessoa.getId()) && Objects.equals(getCpf(), pessoa.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf());
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public void setFilePath(FilePath filePath) {
        this.filePath = filePath;
    }
}
