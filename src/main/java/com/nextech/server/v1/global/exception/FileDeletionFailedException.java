package com.nextech.server.v1.global.exception;

public class FileDeletionFailedException extends RuntimeException {
    public FileDeletionFailedException(String message) {
        super(message);
    }
}
