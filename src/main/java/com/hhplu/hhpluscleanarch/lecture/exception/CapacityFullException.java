package com.hhplu.hhpluscleanarch.lecture.exception;

public class CapacityFullException extends RuntimeException {
    public CapacityFullException(String message) {
        super(message);
    }
}