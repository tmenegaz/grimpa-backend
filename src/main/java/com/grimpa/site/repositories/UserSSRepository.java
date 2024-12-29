package com.grimpa.site.repositories;

import com.grimpa.site.domain.UserSS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserSSRepository extends JpaRepository<UserSS, String> {

    UserDetails findByUsername(String username);
}