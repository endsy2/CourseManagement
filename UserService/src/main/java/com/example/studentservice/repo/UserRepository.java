package com.example.studentservice.repo;

import com.example.studentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Long> {
    Optional <User> findByUsername(String username);
}
