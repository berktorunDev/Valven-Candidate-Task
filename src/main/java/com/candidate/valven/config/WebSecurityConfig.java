package com.candidate.valven.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAutoConfiguration
public class WebSecurityConfig {

    // This class is a Spring configuration class for configuring web security.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // This method configures the security filter chain for HTTP requests.

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
