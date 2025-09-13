package com.example.userservice.service;

import com.example.userservice.dto.StudentDTO;
import com.example.userservice.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class StudentService {
    private UserRepository userRepository;

    @GetMapping("/findStudentById/{id}}")
    public StudentDTO findStudentById(@PathVariable long id) {
        return userRepository.findStudentByIdAndRole(id,"Student");
    }
}
