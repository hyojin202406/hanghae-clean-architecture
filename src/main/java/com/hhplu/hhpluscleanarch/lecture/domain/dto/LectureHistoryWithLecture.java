package com.hhplu.hhpluscleanarch.lecture.domain.dto;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureHistoryWithLecture {
    private Long id;
    private Long userId;
    private Long lectureId;
    private LocalDateTime appliedAt;
    private String lectureTitle;
    private String lectureName;
    private HistoryStatus historyStatus;
}