package com.nextech.server.v1.global.exception;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException(String message) {
        super(message);
    }
}