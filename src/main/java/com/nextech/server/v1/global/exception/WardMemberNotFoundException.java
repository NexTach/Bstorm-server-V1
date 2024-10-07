package com.nextech.server.v1.global.exception;

public class WardMemberNotFoundException extends RuntimeException {
    public WardMemberNotFoundException(String message) {
        super(message);
    }
}