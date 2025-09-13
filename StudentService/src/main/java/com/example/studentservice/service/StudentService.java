package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDtoRequest;
import com.example.studentservice.dto.StudentDtoResponse;
import com.example.studentservice.model.Student;
import com.example.studentservice.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private StudentRepo studentRepo;
    private ModelMapper modelMapper;
    public ResponseEntity<String>createStudent(StudentDtoRequest studentDtoRequest){
        Student student=modelMapper.map(studentDtoRequest,Student.class);
        studentRepo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("student created successfully");
    }
    public ResponseEntity<String>updateStudent(StudentDtoRequest studentDto, long id){
        Student find =studentRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Student not found"));
        Student student=modelMapper.map(studentDto,Student.class);
        student.setName(studentDto.getName());
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setCourse(studentDto.getCourse());
        student.setIsEnabled(studentDto.getIs_enable());
        studentRepo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto.toString());
    }
    public ResponseEntity<String>deleteStudent(@RequestParam long id){
        studentRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted student with id "+id);
    }
    public ResponseEntity<List<StudentDtoResponse>>getALlStudent(){
        List <Student> students=studentRepo.findAll();
        List<StudentDtoResponse> responseList=students.stream()
                .map(student->modelMapper.map(student, StudentDtoResponse.class))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
}
