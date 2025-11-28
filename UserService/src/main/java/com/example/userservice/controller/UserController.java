package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface UserController {
    public ResponseEntity<List<UserDTO>> getAllUsers();
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(String role);
}