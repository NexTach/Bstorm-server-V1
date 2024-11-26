package com.nextech.server.v1.global.exception;

public class MissionListNotFoundException extends RuntimeException {
    public MissionListNotFoundException(String message) {
        super(message);
    }
}