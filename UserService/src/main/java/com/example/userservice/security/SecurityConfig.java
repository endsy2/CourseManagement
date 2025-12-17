package com.example.userservice.security;

//import com.example.userservice.filter.JwtAuthenticationFilter;
//import com.example.userservice.filter.JwtAuthenticationFilter;
//import com.example.userservice.filter.JwtAuthenticationFilter;
import com.example.userservice.filter.JwtAuthenticationFilter;
import com.example.userservice.security.handler.CustomerAccessDeniedHandler;
import com.example.userservice.security.handler.RestAuthenticationEntryPoint;
import com.example.userservice.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // ✅ for logging
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j  // ✅ enables log.info / log.debug
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomerAccessDeniedHandler accessDeniedHandler;
//    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RestAuthenticationEntryPoint restAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        log.info(" Setting up SecurityFilterChain...");

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        log.info(" SecurityFilterChain configured successfully.");
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        log.info("️ Setting up DaoAuthenticationProvider with BCrypt...");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info(" Using BCryptPasswordEncoder.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("AuthenticationManager bean created.");
        return config.getAuthenticationManager();
    }
}
