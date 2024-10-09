package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.service.PasswordValidationService;
import com.nextech.server.v1.global.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidationServiceImpl implements PasswordValidationService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void validatePassword(String password, String confirmPassword) {
        if (!passwordEncoder.matches(confirmPassword, password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }
    }
}