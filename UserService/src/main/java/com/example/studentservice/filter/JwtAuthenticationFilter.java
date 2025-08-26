package com.example.studentservice.filter;

import com.example.studentservice.service.CustomUserDetailsService;
import com.example.studentservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // ✅ Lombok logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j // ✅ Enables "log.info / log.error / log.debug"
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        log.debug("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

        // Check if Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            log.debug("JWT Token detected in Authorization header.");

            try {
                username = jwtUtil.extractUsername(token);
                log.info("Extracted username from JWT: {}", username);
            } catch (Exception e) {
                log.error("JWT token extraction failed: {}", e.getMessage());
            }
        } else {
            log.debug("No valid Authorization header found. Skipping JWT extraction.");
        }

        // If username is valid and no authentication exists yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("Loading user details for: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token)) {
                log.info("JWT token validated for user: {}", username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                log.warn("JWT token validation failed for user: {}", username);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean skip = path.startsWith("/auth/login") || path.startsWith("/auth/register");
        if (skip) {
            log.debug("Skipping JWT filter for public endpoint: {}", path);
        }
        return skip;
    }
}
