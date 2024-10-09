package com.nextech.server.v1.domain.auth.service;

public interface PasswordValidationService {
    void validatePassword(String password, String confirmPassword);
}