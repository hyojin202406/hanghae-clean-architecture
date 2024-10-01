package com.hhplu.hhpluscleanarch.lecture.domain;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lecture_history")
public class LectureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long lectureId;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Column(name = "history_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryStatus historyStatus;
}