package com.grimpa.site.domain.enums;

public enum Modalidade {
    BALLET(0, "BALLET"), CONTEMPORANEO(1, "CONTEMPORANEO"), ALONGAMENTO(2, "ALONGAMENTO"), CRIACAO(3, "CRIACAO");

    private final String descricao;
    private final Integer codigo;

    Modalidade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Modalidade toEnum(Integer codigo) throws IllegalAccessException {
        if (codigo == null) return null;

        for (Modalidade modalidade : Modalidade.values()) {
            if (codigo.equals(modalidade.getCodigo())) return modalidade;
        }
        throw new IllegalAccessException("Modalidade inválida");
    }

    public String getdescricao() {
        return descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
