package com.example.userservice.service;

import com.example.userservice.dto.AuthResponse;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repo.RoleRepository;
import com.example.userservice.repo.UserRepository;
import com.example.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthResponse register(RegisterRequest request) {
        logger.info("Register request received for username: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("Username already taken: {}", request.getUsername());
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setEnabled(true);

        logger.info("User object created: {}", user.getUsername());

        // Assign multiple roles
        Set<Role> userRoles = new HashSet<>();
        if (request.getRoles() != null) {
            userRoles = request.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());

        } else {
            logger.warn("No roles provided for user {}", request.getUsername());
        }
        user.setRoles(userRoles);
        if (userRoles.stream().findAny()=="student") {

        }
        logger.info("Roles set for user {}: {}", user.getUsername(),
                userRoles.stream().map(Role::getName).collect(Collectors.joining(",")));

        userRepository.save(user);
        logger.info("User saved in database: {}", user.getUsername());

        String rolesString = userRoles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(",")); // "student,teacher"

        String token = jwtUtil.generateToken(user.getUsername(), rolesString);
        logger.info("JWT token generated for user {}: {}", user.getUsername(), token);

        return new AuthResponse(token, user.getUsername(), rolesString);
    }




    public AuthResponse login(LoginRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());

        try {
            // Authenticate username & password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            logger.info("Authentication successful for username: {}", request.getUsername());
        } catch (Exception e) {
            logger.error("Authentication failed for username: {}", request.getUsername(), e);
            throw new RuntimeException("Invalid username or password");
        }

        // Load user entity
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", request.getUsername());
                    return new RuntimeException("User not found");
                });
        logger.info("User loaded from database: {}", user.getUsername());

        // Convert roles to comma-separated string
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        logger.info("Roles for user {}: {}", user.getUsername(), roles);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), roles);
        logger.info("JWT token generated for user {}: {}", user.getUsername(), token);

        return new AuthResponse(token, "Bearer", user.getUsername(), roles);
    }
}
