package com.grimpa.site.domain.dtos;

import com.grimpa.site.domain.enums.Perfil;
import com.grimpa.site.domain.enums.Roles;

import java.util.Set;

public record RegisterDto(
        String username,
        String password,
        Set<Roles> roles
) {
}
