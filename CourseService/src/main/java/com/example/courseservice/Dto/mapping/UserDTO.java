package com.example.courseservice.Dto.mapping;

import lombok.*;

import java.util.Set;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id ;
    private String username;
    private String email;
    private Set<String> roles;
}
