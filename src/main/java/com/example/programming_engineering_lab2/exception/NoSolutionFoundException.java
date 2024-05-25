package com.example.programming_engineering_lab2.exception;

public class NoSolutionFoundException extends RuntimeException{
    private String message;
    public NoSolutionFoundException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
