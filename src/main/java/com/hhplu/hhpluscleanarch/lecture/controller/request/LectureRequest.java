package com.hhplu.hhpluscleanarch.lecture.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequest {
    private Long lectureId;
    private Long userId;
}
