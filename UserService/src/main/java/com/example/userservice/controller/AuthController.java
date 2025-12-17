package com.example.userservice.controller;
import com.example.userservice.dto.*;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.RefreshTokenService;
import com.example.userservice.service.implement.AuthImpl;
import com.example.userservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class  AuthController  {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            String token=authHeader.substring(7);
            String username=jwtUtil.extractUsername(token);
            return authService.logout(username);

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    @PostMapping("/refreshToken")
//    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
//        return authService.refr(tokenRefreshRequest);
//    }
    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String,String>> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshTokenRefreshRequest) {
            log.debug(refreshTokenRefreshRequest.getRefreshToken());
        if (!jwtUtil.validateToken(refreshTokenRefreshRequest.getRefreshToken())) {
            log.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtUtil.extractUsername(refreshTokenRefreshRequest.getRefreshToken());
        String role= jwtUtil.extractRole(refreshTokenRefreshRequest.getRefreshToken());
        if (!refreshTokenService.isRefreshTokenValid(refreshTokenRefreshRequest.getRefreshToken(),username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        log.info("Refresh token validated for username: {}", username);
//        log.debug("Refresh token validated for role: {}", Arrays.stream(role.split(",")).toList().getClass());

        String newAccessToken = jwtUtil.generateToken(username,role);
        String newRefreshToken = jwtUtil.generateRefreshToken(username,role);

        refreshTokenService.saveRefreshToken(username, newRefreshToken, 7 * 24 * 60 * 60 * 1000L);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        ));
    }
}