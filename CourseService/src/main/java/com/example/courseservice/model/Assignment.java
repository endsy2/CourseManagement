package com.example.courseservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "assignment",schema = "course_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "title",nullable = true)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name = "due_date",nullable = false)
    private Date dueDate;

    @Column(name = "max_score",nullable = false)
    private Integer maxScore;

    @CurrentTimestamp
    @Column(name = "create_at",nullable = false,updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at",nullable = false)
    private LocalDateTime updateAt;

}
