package com.example.studentservice.service;

import com.example.studentservice.model.Role;
import com.example.studentservice.repo.RoleRepository;
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
