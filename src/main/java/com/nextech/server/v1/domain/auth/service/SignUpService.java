package com.nextech.server.v1.domain.auth.service;

import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import jakarta.validation.Valid;

public interface SignUpService {
    SignUpResponse signUp(@Valid SignUpRequest signUpRequest);
}