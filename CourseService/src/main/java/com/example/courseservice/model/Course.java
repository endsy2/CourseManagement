package com.example.courseservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "courses",schema = "course_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 50)
    private String title;


    @Column(columnDefinition = "TEXT")
    private String description;

    // Instructor ID comes from user-service (not a foreign key, just reference)
    @Column(name = "instructor_id", nullable = false)
    private Integer instructorId;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column (name="start_time",nullable = false)
    private LocalTime startTime;

    @Column (name="end_time",nullable = false)
    private LocalTime endTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany (mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Assignment> assignments;
}
