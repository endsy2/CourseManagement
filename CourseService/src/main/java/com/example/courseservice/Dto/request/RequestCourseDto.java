package com.example.courseservice.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCourseDto {
    private String title;
    private String instructorName;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
}
