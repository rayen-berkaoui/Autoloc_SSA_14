package com.example.autoloc.exception;

/** Request conflicts with the current state of the data (HTTP 409). */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
