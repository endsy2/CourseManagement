package com.example.userservice.service.implement;

import com.example.userservice.dto.*;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repo.RoleRepository;
import com.example.userservice.repo.UserRepository;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.RedisService;
import com.example.userservice.service.RefreshTokenService;
import com.example.userservice.util.JwtUtil;
import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final RefreshTokenService refreshTokenService;
    private final RedisService redisService;

    @Transactional
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
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
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());

        } else {
            logger.warn("No roles provided for user {}", request.getUsername());
        }
        user.setRoles(userRoles);
//        if (userRoles.stream().findAny()=="student") {
//
//        }
        logger.info("Roles set for user {}: {}", user.getUsername(),
                userRoles.stream().map(Role::getName).collect(Collectors.joining(",")));

        userRepository.save(user);
        logger.info("User saved in database: {}", user.getUsername());

        String rolesString = userRoles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(",")); // "student,teacher"

        String accessToken = jwtUtil.generateToken(user.getUsername(), rolesString);
        logger.info("JWT token generated for user {}: {}", user.getUsername(), accessToken);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), rolesString);
        logger.info("JWT token generated for user {}: {}", user.getUsername(), refreshToken);
        redisService.setKey("accessToken:"+user.getUsername(), accessToken);
        redisService.setKey("refreshToken:"+user.getUsername(),refreshToken);
        logger.info(rolesString);
        logger.info(accessToken);
        AuthResponse response = new AuthResponse(accessToken,refreshToken, "Bearer", user.getUsername(), rolesString);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());

        try {
            // Authenticate username & password
            logger.info("Attempting to authenticate for username: {}", request.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            logger.info("Authentication successful for username: {}", request.getUsername());
        } catch (ResourceNotFoundException ex) {
            logger.error("Username not found: {}", request.getUsername(), ex);
            throw new ResourceNotFoundException("User not found: " + request.getUsername());
        } catch (BadCredentialsException ex) {
            logger.error("Invalid credentials for username: {}", request.getUsername(), ex);
            throw new ResourceNotFoundException("Invalid username or password");
        } catch (Exception ex) {
            logger.error("Authentication error for username: {}", request.getUsername(), ex);
            throw new ResourceNotFoundException("Authentication failed");
        }

        // Load user entity
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", request.getUsername());
                    return new ResourceNotFoundException("User not found");
                });
        logger.info("User loaded from database: {}", user.getUsername());

        // Convert roles to comma-separated string
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        logger.info("Roles for user {}: {}", user.getUsername(), roles);

        // Generate JWT token
        String accessToken = jwtUtil.generateToken(user.getUsername(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), roles);
        logger.info("JWT access token generated for user {}: {}", user.getUsername(), accessToken);
        logger.info("JWT refresh token generated for user {}: {}", user.getUsername(), refreshToken);
        redisService.setKey("accessToken:"+user.getUsername(), accessToken);
        redisService.setKey("refreshToken:"+user.getUsername(),refreshToken);
        logger.info("roles for user {}: {}", user.getUsername(), roles);
        AuthResponse response = new AuthResponse(accessToken,refreshToken, "Bearer", user.getUsername(), roles);
        return ResponseEntity.ok( response);
    }
    @Transactional
    public ResponseEntity<String> logout(String username) {
        List<String> keys = List.of(
                "accessToken:" + username,
                "refreshToken:" + username
        );

        keys.forEach(key -> redisService.deleteKey(key));

        return ResponseEntity.ok("User Logged out");
    }

}
