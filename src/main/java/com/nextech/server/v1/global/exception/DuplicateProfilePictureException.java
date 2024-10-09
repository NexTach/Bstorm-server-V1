package com.nextech.server.v1.global.exception;

public class DuplicateProfilePictureException extends RuntimeException {
    public DuplicateProfilePictureException(String message) {
        super(message);
    }
}
