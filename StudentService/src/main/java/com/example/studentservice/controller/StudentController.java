package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentDtoRequest;
import com.example.studentservice.dto.StudentDtoResponse;
import com.example.studentservice.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface  StudentController {
    @PostMapping("/createStudent")
    public ResponseEntity<String> createStudent(@RequestBody StudentDtoRequest studentDto);
    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent( @PathVariable long id);
    @PutMapping("/updateStudent")
    public ResponseEntity<String>updateStudent(@RequestBody StudentDtoRequest studentDto, @PathVariable long id);
    @GetMapping("/getAllStudent")
    public ResponseEntity<List<StudentDtoResponse>>getALlStudent();
}
