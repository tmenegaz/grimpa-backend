package com.grimpa.site.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey key;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @PostConstruct
    public void init() {
        byte[] secretBytes = secret.getBytes();
        key = Keys.hmacShaKeyFor(secretBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (!claims.isEmpty()) {
            String userName = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            return isTokenValid(userName, expirationDate, now);
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTokenValid(String userName, Date expirationDate, Date now) {
        return userName != null && expirationDate != null && now.before(expirationDate);
    }

    public String getUserame(String token) {
        Claims claims = getClaims(token);
        if (!claims.isEmpty()) {
            return claims.getSubject();
        }
        return null;
    }
}
