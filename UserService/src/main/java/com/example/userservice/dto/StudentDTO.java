package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class StudentDTO {
    private Long id; // keep Long if you want

    private String username;
    private String email;

    // Constructor matches entity type
    public StudentDTO(int id, String username, String email) {
        this.id = (long) id; // convert int to Long
        this.username = username;
        this.email = email;
    }
}

