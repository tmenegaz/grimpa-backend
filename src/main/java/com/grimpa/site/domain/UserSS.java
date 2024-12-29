package com.grimpa.site.domain;

import com.grimpa.site.domain.enums.Roles;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "userss")
@Entity(name = "userss")
public class UserSS implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Set<Roles> roles;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    private String password;

    public UserSS() {
    }

    public UserSS(String id, String username, String password, Set<Roles> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserSS(String username, String password, Set<Roles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return password;
    }

    public void setEmail(String password) {
        this.password = UserSS.this.password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getDescricao()))
                .collect(Collectors.toList());

        boolean isAdmin = roles.contains(Roles.ADMIN);

        if (isAdmin) {
            authorities.clear();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            authorities.clear();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
