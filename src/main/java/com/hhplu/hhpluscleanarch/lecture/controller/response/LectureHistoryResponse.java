package com.hhplu.hhpluscleanarch.lecture.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureHistoryResponse {
    private Long lectureId;
    private String lectureName;
    private String lecturerName;
    private LocalDateTime appliedAt;
    private String applicationStatus;
}