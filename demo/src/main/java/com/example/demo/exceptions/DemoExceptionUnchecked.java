package com.example.demo.exceptions;

public class DemoExceptionUnchecked extends RuntimeException {
    public DemoExceptionUnchecked(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
