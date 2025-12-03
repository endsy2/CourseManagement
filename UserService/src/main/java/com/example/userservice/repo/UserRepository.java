package com.example.userservice.repo;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
//import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    //    List<User>findByRoles(String role);
//    List<User>findbyIdandRoles_Name(Long id,String role);
    List<User> findByRoles_Name(String name);

    //    @Query("SELECT new com.example.userservice.dto.StudentDTO(u.id, u.username, u.email) " +
//            "FROM User u JOIN u.roles r WHERE u.id = :id AND r.name = :roleName")
//    StudentDTO findStudentByIdAndRole(@Param("id") Long id, @Param("roleName") String roleName);
//    @Query("SELECT u.id,u.username,u.email, FROM User u")
//    List<UserDTO> findAllUsers();
//
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findAllUsersByRoleName(@Param("roleName") String roleName);
    Optional<User> findById(Integer id);
}
