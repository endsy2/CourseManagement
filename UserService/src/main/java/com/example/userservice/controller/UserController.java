


package com.example.userservice.controller;


import com.example.userservice.controller.UserController;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController  {
    private final UserService userService;


    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("getByRole")
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam String role) {
        List<UserDTO>users=userService.findAllUsersByRole(role);
        return ResponseEntity.ok(users);
    }


    @GetMapping("getUserByName")
    public ResponseEntity<UserDTO> getUserByName(@RequestParam("name") String name) {
        UserDTO users=userService.findUserByName(name);
        return ResponseEntity.ok(users);
    }


    @GetMapping("getById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
        UserDTO users=userService.findById(id);
        return ResponseEntity.ok(users);
    }
}
