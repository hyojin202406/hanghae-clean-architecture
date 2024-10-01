package com.hhplu.hhpluscleanarch.lecture.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureRequest {
    private Long lectureId;
    private Long userId;
}
