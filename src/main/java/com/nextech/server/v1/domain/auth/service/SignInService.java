package com.nextech.server.v1.domain.auth.service;

import com.nextech.server.v1.domain.auth.dto.request.SignInRequest;
import com.nextech.server.v1.global.dto.response.TokenResponse;

public interface SignInService {
    TokenResponse signIn(SignInRequest signInRequest);
}
