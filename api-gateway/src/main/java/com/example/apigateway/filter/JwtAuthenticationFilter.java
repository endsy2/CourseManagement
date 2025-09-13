package com.example.apigateway.filter;

import com.example.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String[] OPEN_API_ENDPOINTS = {
            "/user-service/auth/login",
            "/user-service/auth/register",
            "/user-service/auth/refresh-token"
    };

    private boolean isOpenEndpoint(String path) {
        for (String open : OPEN_API_ENDPOINTS) {
            if (path.startsWith(open)) return true;
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod() != null
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";

        log.info("Incoming request: {} {}", method, path);

        if (isOpenEndpoint(path)) {
            log.info("Open endpoint, skipping JWT filter: {}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for request: {} {}", method, path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        log.info("JWT token detected for request: {} {}", method, path);

        try {
            Claims claims = JwtUtil.validateToken(token);

            String username = claims.getSubject();
            String roles = claims.get("roles", String.class);

            log.info("JWT validated. User: {}, Roles: {}", username, roles);

            // Forward headers to downstream
            exchange = exchange.mutate()
                    .request(r -> r.headers(h -> {
                        h.set(HttpHeaders.AUTHORIZATION, "Bearer " + token); // forward original token
                    }))
                    .build();

        } catch (Exception e) {
            log.error("JWT validation failed for request: {} {}. Error: {}", method, path, e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // run before routing
    }
}
