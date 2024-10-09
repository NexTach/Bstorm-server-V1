package com.nextech.server.v1.global.exception;

public class DuplicateRoleAssignmentException extends RuntimeException {
    public DuplicateRoleAssignmentException(String message) {
        super(message);
    }
}
