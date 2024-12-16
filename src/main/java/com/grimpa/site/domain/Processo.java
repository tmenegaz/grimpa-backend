package com.grimpa.site.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grimpa.site.domain.enums.Modalidade;
import com.grimpa.site.domain.enums.Status;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Processo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
    private Modalidade modalidade;
    private Status status;
    private String titulo;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    public Processo() {
        super();
    }

    public Processo(Integer id, Modalidade modalidade, Status status, String titulo, String observacao, Cliente cliente, Tecnico tecnico) {
        this.id = id;
        this.modalidade = modalidade;
        this.status = status;
        this.titulo = titulo;
        this.observacao = observacao;
        this.cliente = cliente;
        this.tecnico = tecnico;
    }

    public Processo toModalidadeProcesso(Integer modalidade) {
        Processo processo = new Processo();
        try {
            processo.setModalidade(Modalidade.toEnum(modalidade));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return processo;
    }

    public Processo toStatusProcesso(Integer status) {
        Processo processo = new Processo();
        try {
            processo.setStatus(Status.toEnum(status));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return processo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Processo processo)) return false;
        return Objects.equals(getId(), processo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
