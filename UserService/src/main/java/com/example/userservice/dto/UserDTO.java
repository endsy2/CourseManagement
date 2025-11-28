package com.example.userservice.dto;

import com.example.userservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id ;
    private String username;
    private String email;
    private Set<Role> roles;
}
