package com.grimpa.site.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grimpa.site.domain.UserSS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    public String generateToken(UserSS userSS) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            String token = JWT.create()
                    .withIssuer("grimpa-site")
                    .withSubject(userSS.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .withClaim(
                            "roles", userSS.getAuthorities()
                                    .stream().map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()
                                    )
                    )
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro durante a geração do token", exception);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
    }

    public String tokenValido(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("grimpa-site")
                    .build()
                    .verify(token);

            String username = decodedJWT.getSubject();
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                    .asList(String.class).stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            return username;
        } catch (JWTVerificationException exception) {
            return null;

        }
    }

}
