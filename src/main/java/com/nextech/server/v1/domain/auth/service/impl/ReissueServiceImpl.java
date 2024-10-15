package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.response.ReissueResponse;
import com.nextech.server.v1.domain.auth.service.ReissueService;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReissueServiceImpl implements ReissueService {

    private final JwtTokenService jwtTokenService;

    @Override
    public ReissueResponse reissue(String refreshToken) {
        TokenResponse intactTokenResponse = jwtTokenService.reissueToken(refreshToken);
        return new ReissueResponse(
                intactTokenResponse.getAccessToken(),
                intactTokenResponse.getAccessTokenExpiresIn(),
                intactTokenResponse.getRoles()
        );
    }
}