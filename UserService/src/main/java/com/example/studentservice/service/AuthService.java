package com.example.studentservice.service;

import com.example.studentservice.dto.AuthResponse;
import com.example.studentservice.dto.LoginRequest;
import com.example.studentservice.dto.RegisterRequest;
import com.example.studentservice.model.Role;
import com.example.studentservice.model.User;
import com.example.studentservice.repo.RoleRepository;
import com.example.studentservice.repo.UserRepository;
import com.example.studentservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);

        // Assign multiple roles
        Set<Role> userRoles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(userRoles);

        userRepository.save(user);

        String rolesString = userRoles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(",")); // "student,teacher"

        String token = jwtUtil.generateToken(user.getUsername(), rolesString);

        return new AuthResponse(token, user.getUsername(), rolesString);
    }




    public AuthResponse login(LoginRequest request) {
        // Authenticate username & password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Load user entity
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert roles to comma-separated string
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), roles);

        return new AuthResponse(token, "Bearer", user.getUsername(), roles);
    }
}
