package com.hhplu.hhpluscleanarch.lecture.domain.dto;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.response.UserHistoryResponse;
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

    public UserHistoryResponse toUserHistoryResponse() {
        return new UserHistoryResponse(
                this.id,
                this.userId,
                this.lectureId,
                this.appliedAt,
                this.lectureTitle,
                this.lectureName,
                this.historyStatus.toString()
        );
    }
}