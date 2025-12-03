package com.example.courseservice.controller;
import com.example.courseservice.Dto.mapping.CourseDTO;
import com.example.courseservice.Dto.request.RequestCourseDto;
import com.example.courseservice.Dto.response.ResponseCourseDto;
import com.example.courseservice.model.Course;
import com.example.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/getCourse")
    public ResponseEntity <List<Course>> getCourse() {
        List<Course> course=courseService.getCourse();
        return ResponseEntity.ok(course);
    }
    @GetMapping("/getCourseByTitle")
    public ResponseEntity<List<CourseDTO>> getCourseByName(@RequestParam String title) {
        List<CourseDTO> course =courseService.getCourseByName(title);
        return ResponseEntity.ok(course);
    }
    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable("id") int id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    @PostMapping("/createCourse")
    public ResponseEntity<ResponseCourseDto> createCourse(@RequestBody RequestCourseDto requestCourseDto) {
        ResponseCourseDto responseCourseDto =courseService.createCourse(requestCourseDto);
        return ResponseEntity.ok(responseCourseDto) ;
    }
    @PutMapping("/editCourse/{id}")
    public ResponseEntity<String> editCourse(@RequestBody RequestCourseDto requestCourseDto,@PathVariable("id") int id) {
        ResponseCourseDto editCourseDto1=courseService.editCourse(requestCourseDto,id);
        return ResponseEntity.ok("done");
    }
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") int id) {
        String course=courseService.deleteCourseById(id);
        return ResponseEntity.ok(course);
    }
}