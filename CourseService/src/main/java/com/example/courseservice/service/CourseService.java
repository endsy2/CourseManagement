


package com.example.courseservice.service;

import com.example.courseservice.Dto.mapping.CourseDTO;
import com.example.courseservice.Dto.request.RequestCourseDto;
import com.example.courseservice.Dto.response.ResponseCourseDto;
import com.example.courseservice.model.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getCourse();
    public ResponseCourseDto createCourse(RequestCourseDto requestCourseDto);
    public List<CourseDTO>getCourseByName(String title);
    public CourseDTO getCourseById(int id);
    public ResponseCourseDto editCourse(RequestCourseDto requestCourseDto, int id );
    public String deleteCourseById(int id);
}
