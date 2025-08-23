package com.example.courseservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("course-service")
public class CourseController {
    @GetMapping("test")
    public String test (){
        return "test";
    }
    @GetMapping("/course")getCourse(){

    }
}
