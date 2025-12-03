package com.example.courseservice.service.implement;

import com.example.courseservice.Dto.mapping.CourseDTO;
import com.example.courseservice.Dto.request.RequestCourseDto;
import com.example.courseservice.Dto.mapping.UserDTO;
import com.example.courseservice.Dto.response.ResponseCourseDto;
import com.example.courseservice.client.UserServiceClient;
import com.example.courseservice.model.Course;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserServiceClient userServiceClient;
    private static final String COURSE_CACHE = "courses";

    @Override
    @CachePut(value = "course-cache", key = "#result.id")
    public ResponseCourseDto createCourse(RequestCourseDto requestCourseDto) {
        UserDTO response = userServiceClient.getUserByName(requestCourseDto.getInstructorName());

        if (response.getRoles() == null || !response.getRoles().contains("teacher")) {
            throw new IllegalArgumentException("Instructor must have teacher role");
        }

        Course course = new Course();
        course.setTitle(requestCourseDto.getTitle());
        course.setDescription(requestCourseDto.getDescription());
        course.setInstructorId(response.getId());
        course.setStartTime(requestCourseDto.getStartTime());
        course.setEndTime(requestCourseDto.getEndTime());

        Course savedCourse = courseRepository.save(course);

        return new ResponseCourseDto(
                savedCourse.getId(),
                savedCourse.getTitle(),
                requestCourseDto.getInstructorName(),
                savedCourse.getDescription(),
                savedCourse.getStartTime(),
                savedCourse.getEndTime()
        );
    }
    @Override
    public List<Course> getCourse() {
        List<Course> course=courseRepository.findAll();
        return course;
    }

    @Override
    @Cacheable(value = COURSE_CACHE,key = "#title")
    public List<CourseDTO> getCourseByName(String title) {
        List<Course> courses=courseRepository.findByTitle(title);
        ModelMapper modelMapper = new ModelMapper();
        List<CourseDTO>courseDTOS=courses.stream()
                .map(course -> modelMapper.map(course,CourseDTO.class))
                .toList();
        return courseDTOS;
    }

    @Override
    @Cacheable(value = COURSE_CACHE,key = "#id")
    public CourseDTO getCourseById(int id) {
        Course course= courseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Course Not Found"));
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(course,CourseDTO.class);
    }

    @Override
    public ResponseCourseDto editCourse(RequestCourseDto requestCourseDto, int id ) {
       Course course =courseRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Course Not Found"));
       UserDTO users=userServiceClient.getUserByName(requestCourseDto.getInstructorName());
       int instructorId;
       if (users.getRoles().contains("teacher")) {
           instructorId=users.getId();
       }
       else {
           throw new IllegalArgumentException("Instructor must have teacher role");
       }
       course.setTitle(requestCourseDto.getTitle());
       course.setDescription(requestCourseDto.getDescription());
       course.setInstructorId(instructorId);
       course.setStartTime(requestCourseDto.getStartTime());
       course.setEndTime(requestCourseDto.getEndTime());
       courseRepository.save(course);
        return new ResponseCourseDto(
                course.getId(),
                course.getTitle(),
                requestCourseDto.getInstructorName(),
                course.getDescription(),
                course.getStartTime(),
                course.getEndTime()
        );
    }

    @Override
    public String deleteCourseById(int id) {
        Course course=courseRepository.findById(id)
                .orElseThrow (()->new RuntimeException("Course Not Found"));
        courseRepository.delete(course);
        return "Delete success";
    }
}
