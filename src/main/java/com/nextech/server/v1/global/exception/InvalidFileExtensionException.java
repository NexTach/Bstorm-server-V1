package com.nextech.server.v1.global.exception;

public class InvalidFileExtensionException extends RuntimeException {
    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
