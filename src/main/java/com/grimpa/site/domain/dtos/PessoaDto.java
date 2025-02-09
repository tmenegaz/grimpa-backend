package com.grimpa.site.domain.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PessoaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Set<Integer> perfis = new HashSet<>();

    public PessoaDto() {
        super();
    }

    public PessoaDto(Set<Integer> perfis) {
        this.perfis = perfis;
    }

    public Set<Integer> getPerfis() {
        return perfis;
    }
}
