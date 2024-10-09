package com.nextech.server.v1.global.exception;

public class MaxFileSizeExceededException extends RuntimeException {
    public MaxFileSizeExceededException(String message) {
        super(message);
    }
}
