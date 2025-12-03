package com.example.courseservice.Dto.mapping;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    Integer id;
    String title;
    String description;
    Integer instructor_id;
    Boolean isEnabled;
    LocalTime startTime;
    LocalTime endTime;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
