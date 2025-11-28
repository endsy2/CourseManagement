package com.example.userservice.implement;


import com.example.userservice.controller.UserController;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(String role) {
        List<UserDTO>users=userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
