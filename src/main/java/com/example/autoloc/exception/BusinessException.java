package com.example.autoloc.exception;

/** Invalid request from a business point of view (HTTP 400). */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
