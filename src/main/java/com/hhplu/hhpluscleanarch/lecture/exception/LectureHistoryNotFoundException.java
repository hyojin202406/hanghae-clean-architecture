package com.hhplu.hhpluscleanarch.lecture.exception;

public class LectureHistoryNotFoundException extends RuntimeException {
    public LectureHistoryNotFoundException(String message) {
        super(message);
    }
}