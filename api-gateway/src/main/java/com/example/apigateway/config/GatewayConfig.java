package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                User Service
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8080"))
                .route("course-service", r -> r.path("/course-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8090"))
                .build();
    }
}
