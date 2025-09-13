package com.example.studentservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDtoResponse {
    private long id ;
    private String name;
    private String email;
    private String course;
    private Boolean is_enable;
    private long userid;
}
