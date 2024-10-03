package com.hhplu.hhpluscleanarch.lecture.exception;

public class LectureAlreadyAppliedException extends RuntimeException {
    public LectureAlreadyAppliedException(String message) {
        super(message);
    }
}