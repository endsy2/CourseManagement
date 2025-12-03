package com.example.userservice.service;
import com.example.userservice.dto.AuthResponse;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    public AuthResponse register( RegisterRequest request);
    public AuthResponse login(@RequestBody LoginRequest request);
}