package com.nextech.server.v1.global.exception;

public class RelationAlreadyExistsException extends RuntimeException {
    public RelationAlreadyExistsException(String message) {
        super(message);
    }
}
