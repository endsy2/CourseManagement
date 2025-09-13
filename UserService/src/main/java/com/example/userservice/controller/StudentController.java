package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class StudentController {
    @GetMapping("test")
    public String test(){
        return "test";
    }
}
