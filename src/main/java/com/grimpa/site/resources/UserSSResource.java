package com.grimpa.site.resources;

import com.grimpa.site.domain.UserSS;
import com.grimpa.site.domain.dtos.RegisterDto;
import com.grimpa.site.domain.enums.Roles;
import com.grimpa.site.repositories.UserSSRepository;
import com.grimpa.site.security.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/auth")
public class UserSSResource {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserSSRepository repository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto registerDto) {
        if (this.repository.findByUsername(registerDto.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = encoder.encode(registerDto.password());
        Set<Roles> roles = registerDto.roles();
        if (roles == null || roles.isEmpty()) {
            roles = Set.of(Roles.USER);
        }

        UserSS userSS = new UserSS(registerDto.username(), encryptedPassword, roles);

        this.repository.save(userSS);
        return ResponseEntity.ok().build();

    }
}
