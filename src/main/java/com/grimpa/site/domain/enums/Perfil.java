package com.grimpa.site.domain.enums;

public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");

    private Integer codigo;
    private String descricao;

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

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
