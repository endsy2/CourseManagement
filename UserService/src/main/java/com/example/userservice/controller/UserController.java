package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepository;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @GetMapping("/test")
    public String test() { return "ok"; }

    @GetMapping("/finduser/{id}")
    public UserDTO findUserByid(@PathVariable int id){
        return userService.findById(id);
    }
    @GetMapping("/finduserbyname")
    public UserDTO findUserByName(@RequestParam String name){
        return userService.findUserByName(name);
    }



}
