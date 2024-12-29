package com.grimpa.site.domain.enums;

public enum Excluido {
    NAO_EXCLUIDO(0, "NAO_EXCLUIDO"),
    EXCLUIDO(1, "EXCLUIDO");

    private final String descricao;
    private final Integer codigo;

    Excluido(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Excluido toEnum(Integer codigo) throws IllegalAccessException {
        if (codigo == null) return null;

        for (Excluido status : Excluido.values()) {
            if (codigo.equals(status.getCodigo())) return status;
        }
        throw new IllegalAccessException("Exclusão inválida");
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
