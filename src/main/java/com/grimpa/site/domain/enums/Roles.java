package com.grimpa.site.domain.enums;

public enum Roles {
    ADMIN(0, "ADMIN"),
    USER(1, "USER"),
    SUDO(2, "SUDO");

    private final Integer codigo;
    private final String descricao;

    Roles(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Roles toEnum(Integer codigo) {
        if (codigo == null) return null;

        for (Roles role : Roles.values()) {
            if (codigo.equals(role.getCodigo())) return role;
        }
        try {
            throw new IllegalAccessException("Papel inválido");
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
