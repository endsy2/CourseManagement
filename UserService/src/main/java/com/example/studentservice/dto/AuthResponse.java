package com.example.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;  // JWT token
    private String tokenType = "Bearer"; // Optional, defaults to Bearer
    private String username;
    private String roles; // Optional: you can return all roles as comma-separated string

    public AuthResponse(String token, String username, String roles) {
        this.accessToken = token;
        this.username = username;
        this.roles = roles;
    }
}
