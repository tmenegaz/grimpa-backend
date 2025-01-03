package com.grimpa.site.config;

import com.grimpa.site.domain.enums.Roles;
import com.grimpa.site.security.JWTService;
import com.grimpa.site.security.JwtAuthenticationFilter;
import com.grimpa.site.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {
    private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};

    @Autowired
    private Environment env;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private JWTService jwtService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            httpSecurity.headers(ambientProperty -> ambientProperty.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        }
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors((corsCustomizer) -> corsCustomizer
                        .configurationSource(corsConfigurationSource()))
                .sessionManagement((sessionManagerCustomizer) -> sessionManagerCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(PUBLIC_MATCHERS).hasRole(Roles.SUDO.getDescricao())
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole(Roles.SUDO.getDescricao())

                        .requestMatchers(HttpMethod.GET, "/clientes").hasRole(Roles.SUDO.getDescricao())
                        .requestMatchers(HttpMethod.GET, "/tecnicos").hasRole(Roles.SUDO.getDescricao())

                        .requestMatchers(HttpMethod.GET, "/tecnicos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.POST, "/tecnicos").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.PUT, "/tecnicos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.DELETE, "/tecnicos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())

                        .requestMatchers(HttpMethod.GET, "/clientes/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.POST, "/clientes").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.PUT, "/clientes/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.DELETE, "/clientes/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())

                        .requestMatchers(HttpMethod.GET, "/processos").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.GET, "/processos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.POST, "/processos").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.PUT, "/processos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.DELETE, "/processos/*").hasAnyRole(Roles.SUDO.getDescricao(), Roles.ADMIN.getDescricao())
                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtService))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(Objects.requireNonNull(env.getProperty("cors.allowed.origins"))));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
