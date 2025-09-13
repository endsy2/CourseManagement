package com.example.studentservice.implement;

import com.example.studentservice.controller.StudentController;
import com.example.studentservice.dto.StudentDtoRequest;
import com.example.studentservice.dto.StudentDtoResponse;
import com.example.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentImplement implements StudentController {

    private StudentService studentService;

    @Override
        public ResponseEntity<String>createStudent(@RequestBody StudentDtoRequest studentDto){
          return studentService.createStudent(studentDto);
    }
    @Override
    public ResponseEntity<String>deleteStudent(@RequestParam long id){
        return studentService.deleteStudent(id);
    }
    @Override
    public ResponseEntity<String>updateStudent(@RequestBody StudentDtoRequest studentDto, @PathVariable long id){
        return studentService.updateStudent(studentDto,id);
    }
    @Override
    public ResponseEntity<List<StudentDtoResponse>>getALlStudent(){
        return studentService.getALlStudent();
    }
}
