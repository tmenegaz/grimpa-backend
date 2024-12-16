package com.grimpa.site.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grimpa.site.domain.Cliente;
import com.grimpa.site.domain.Processo;
import com.grimpa.site.domain.Tecnico;
import com.grimpa.site.domain.enums.Modalidade;
import com.grimpa.site.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ProcessoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "O campo DATA INICIO é requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataInicio = LocalDate.now();

    @NotNull(message = "O campo DATA FIM é requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFim;

    private Integer modalidade;
    private Integer status;

    private String titulo;
    private String observacao;

    private Integer tecnico;
    private String nomeTecnico;

    private Integer cliente;
    private String nomeCliente;

    public ProcessoDto() {
        super();
    }

    public ProcessoDto(Processo processo) {
        this.id = processo.getId();
        this.dataInicio = processo.getDataInicio();
        this.dataFim = processo.getDataFim();
        this.modalidade = processo.getModalidade().getCodigo();
        this.status = processo.getStatus().getCodigo();
        this.titulo = processo.getTitulo();
        this.observacao = processo.getObservacao();
        this.tecnico = processo.getTecnico().getId();
        this.nomeTecnico = processo.getTecnico().getNome();
        this.cliente = processo.getCliente().getId();
        this.nomeCliente = processo.getCliente().getNome();
    }

    public ProcessoDto toModalidadeProcessoDto(Modalidade modalidade) {
        ProcessoDto processoDto = new ProcessoDto();
            processoDto.setModalidade(modalidade.getCodigo());
        return processoDto;
    }

    public ProcessoDto toStatusProcessoDto(Status status) {
        ProcessoDto processoDto = new ProcessoDto();
            processoDto.setStatus(status.getCodigo());
        return processoDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "O campo DATA INICIO é requerido") LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(@NotNull(message = "O campo DATA INICIO é requerido") LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public @NotNull(message = "O campo DATA FIM é requerido") LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(@NotNull(message = "O campo DATA FIM é requerido") LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getModalidade() {
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getTecnico() {
        return tecnico;
    }

    public void setTecnico(Integer tecnico) {
        this.tecnico = tecnico;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }

    public void setNomeTecnico(String nomeTecnico) {
        this.nomeTecnico = nomeTecnico;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
