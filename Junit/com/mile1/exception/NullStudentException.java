package com.mile1.exception;

public class NullStudentException extends Exception {
    public NullStudentException() {
        super("Student object is null");
    }
}
