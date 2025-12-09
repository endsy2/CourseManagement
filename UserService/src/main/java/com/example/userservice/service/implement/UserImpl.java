package com.example.userservice.service.implement;

import com.example.userservice.config.DirectExchangeConfig;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.producer.Producer1;
import com.example.userservice.repo.UserRepository;
import com.example.userservice.service.UserService;
import feign.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final Producer1 producer1;

//    public UserImpl(UserRepository userRepository, Producer1 producer1) {
//        this.userRepository = userRepository;
//        this.producer1 = producer1;
//    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDto = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                ))
                .toList();  // .collect(Collectors.toList()) if using Java < 16
        return userDto;
    }

    public List<UserDTO> findAllUsersByRole(String roleName) {
        List<User> users = userRepository.findAllUsersByRoleName(roleName);
//        log.debug("Loading user details for: {}", users.stream().map(User::getRoles).map(userRoles -> userRoles.stream().map(Role::getName)).collect(Collectors.toSet()));
        return users.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList()); // <- collect into List<UserDTO>
    }



    public UserDTO findUserByName( String name) {
        User user=new User();
        user=userRepository.findByUsername(name)
                .orElseThrow(()->new RuntimeException("user not found"+name));
        return new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }

    public UserDTO findById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Map entity to DTO
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(),user.getRoles().stream()
                                                                                            .map(Role::getName)
                                                                                            .collect(Collectors.toSet()));
    }
    private static final String FILE_PATH = "messages.txt";

//    @RabbitListener(queues = DirectExchangeConfig.QUEUE)
//    public void receiveMessage(String message) {
//        System.out.println("Received message: " + message);
//    }
}
