package com.hhplu.hhpluscleanarch.lecture.domain;

import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "lecture_name", nullable = false)
    private String lecturerName;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "lecture_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "lecture_status", nullable = false)
    @Enumerated(EnumType.STRING) // Enum 타입으로 저장하기 위해 추가
    private LectureStatus lectureStatus;
}