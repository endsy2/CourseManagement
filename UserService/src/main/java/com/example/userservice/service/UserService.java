package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepository;
import feign.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @GetMapping
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDto = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles()
                ))
                .toList();  // .collect(Collectors.toList()) if using Java < 16
        return userDto;
    }
    public List<UserDTO> findAllUsersByRole(String role) {
        List <User>users=userRepository.findAll();
        List <UserDTO>filter =users.stream()
                .filter(user->user.getRoles().equals(role))
                .map(user -> new UserDTO(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRoles()
                        )
                ).toList();
        return filter;
    }

    @GetMapping
    public UserDTO findUserByName(@RequestParam String name) {
        User user=new User();
        user=userRepository.findByUsername(name)
                .orElseThrow(()->new RuntimeException("user not found"+name));
        return new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getRoles());
    }
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Map entity to DTO
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(),user.getRoles());
    }

}
