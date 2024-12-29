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
                .cors((corsCustomizer) -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .sessionManagement((sessionManagerCustomizer) -> sessionManagerCustomizer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tecnicos").hasAnyRole(Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.PUT, "/tecnicos/*").hasAnyRole(Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.DELETE, "/tecnicos/*").hasRole(Roles.ADMIN.getDescricao())
                        .requestMatchers(HttpMethod.POST, "/clientes").hasAnyRole(Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.PUT, "/clientes/*").hasAnyRole(Roles.ADMIN.getDescricao(), Roles.USER.getDescricao())
                        .requestMatchers(HttpMethod.DELETE, "/clientes/*").hasRole(Roles.ADMIN.getDescricao())
                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtService))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
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
