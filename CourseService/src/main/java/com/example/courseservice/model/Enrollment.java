package com.example.courseservice.model;

import com.example.courseservice.model.Enum.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrolllment",schema = "course_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id",nullable = false)
    private Integer studentId;

    @Column(name = "course_id",nullable = false)
    private Integer courseId;

    @CurrentTimestamp
    @Column(name = "enroll_at",nullable = false)
    private LocalDateTime enrollAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CourseStatus status;
}

