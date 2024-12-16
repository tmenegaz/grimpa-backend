package com.grimpa.site.config;

import com.grimpa.site.services.DbService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean(initMethod = "startDbH2")
    public DbService dbService() {
        return new DbService();
    }
}
