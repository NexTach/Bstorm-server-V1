package com.nextech.server.v1.global.exception;

public class MissingFileNameException extends RuntimeException {
    public MissingFileNameException(String message) {
        super(message);
    }
}