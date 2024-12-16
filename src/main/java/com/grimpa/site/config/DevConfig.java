package com.grimpa.site.config;

import com.grimpa.site.services.DbService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    @Bean(initMethod = "startDbPostgreSql")
    public DbService dbService() {
        if (value.equals("create")) return new DbService();
        return null;
    }
}
