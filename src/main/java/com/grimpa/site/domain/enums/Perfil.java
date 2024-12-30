package com.grimpa.site.domain.enums;

public enum Perfil {

    CLIENTE(0, "CLIENTE"),
    TECNICO(1, "TECNICO"),
    AUXILIAR(2, "AUXILIAR");

    private final Integer codigo;
    private final String descricao;

    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer codigo) {
        if (codigo == null) return null;

        for (Perfil perfil : Perfil.values()) {
            if (codigo.equals(perfil.getCodigo())) return perfil;
        }
        try {
            throw new IllegalAccessException("Perfil inválido");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
