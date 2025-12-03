package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserService {
    public List<UserDTO> findAllUsers();
    public List<UserDTO> findAllUsersByRole(@RequestParam String role);
    public UserDTO findUserByName(@RequestParam String name);
    public UserDTO findById(@PathVariable int id);
}
