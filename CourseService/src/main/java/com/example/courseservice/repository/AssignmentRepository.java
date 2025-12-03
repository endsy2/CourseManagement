package com.example.courseservice.repository;

import com.example.courseservice.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {

}
