package com.example.userservice.service;
import com.example.userservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    public ResponseEntity<AuthResponse> register( RegisterRequest request);
    public ResponseEntity<AuthResponse> login(LoginRequest request);
    public ResponseEntity<String> logout(String username);
}