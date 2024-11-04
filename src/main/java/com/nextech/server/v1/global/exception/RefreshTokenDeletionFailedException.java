package com.nextech.server.v1.global.exception;

public class RefreshTokenDeletionFailedException extends RuntimeException {
    public RefreshTokenDeletionFailedException(String message) {
        super(message);
    }
}