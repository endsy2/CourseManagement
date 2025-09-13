package com.example.userservice.service;

import com.example.userservice.model.Role;
import com.example.userservice.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<Role> createRole(Role role){
        Role savedRole;
        savedRole = roleRepository.save(role);
        return ResponseEntity.ok().body(savedRole);
    }
}
