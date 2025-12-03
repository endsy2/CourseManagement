package com.example.courseservice.repository;

import com.example.courseservice.model.UserCourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseHistoryRepository extends JpaRepository<UserCourseHistory,Integer> {
}
