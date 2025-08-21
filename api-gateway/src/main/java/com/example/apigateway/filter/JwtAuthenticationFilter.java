package com.example.apigateway.filter;

import com.example.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
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

   private static final String[] OPEN_API_ENDPOINTS={
           "/users/login",
           "/users/register",
           "/users/refresh-token"
   };//THE OPEN ENDPOINT
    private boolean isOpenEndpoint(String path) {
        for (String open : OPEN_API_ENDPOINTS) {
            if (path.startsWith(open)) return true;
        }
        return false;
    }//LOOP ALL THE ROUTE THAT IS ACROSS THIS GATEWAY
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { //ServerWebExchange it handle all request and response ,GatewayFilterChain is a sequence of filters
        String path = exchange.getRequest().getURI().getPath();

        if (isOpenEndpoint(path)) {
            return chain.filter(exchange); // skip security
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);//Reads the Authorization header from the request.

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }//If header is missing or doesn’t start with "Bearer " → return 401 Unauthorized.

        String token = authHeader.substring(7);

        try {
            Claims claims = JwtUtil.validateToken(token);

            // You can extract roles & add them to headers for downstream services
            String role = claims.get("role", String.class);

            exchange = exchange.mutate()
                    .request(r -> r.headers(h -> h.add("X-User-Role", role)))
                    .build();

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);








        @Override
    public int getOrder() {
        return -1; // run before routing
    }
}
