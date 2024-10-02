package com.hhplu.hhpluscleanarch.lecture.controller.response;

import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureResponse {
    private Long id;
    private String title;
    private String lecturerName;
    private Integer capacity;
    private LectureStatus lectureStatus;
}