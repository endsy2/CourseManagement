package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Open endpoints
                        .pathMatchers("/user-service/auth/**").permitAll()
                        // Allow OPTIONS preflight
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        // Admin endpoints
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        // Everything else requires JWT
                        .anyExchange().authenticated()
                )
                .build();
    }
}
