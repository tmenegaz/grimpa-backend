package com.grimpa.site.domain.enums;

public enum Status {
    ATIVO(0, "ATIVO"), PAUSADO(1, "PAUSADO"), INATIVO(2, "INATIVO");

    private final String descricao;
    private final Integer codigo;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Status toEnum(Integer codigo) throws IllegalAccessException {
        if (codigo == null) return null;

        for (Status status : Status.values()) {
            if (codigo.equals(status.getCodigo())) return status;
        }
        throw new IllegalAccessException("Status inválido");
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
