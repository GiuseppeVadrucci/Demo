package com.example.demo.exceptions;

public class DemoException extends Exception {
    public DemoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}

