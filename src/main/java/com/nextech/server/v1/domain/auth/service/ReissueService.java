package com.nextech.server.v1.domain.auth.service;

import com.nextech.server.v1.domain.auth.dto.response.ReissueResponse;

public interface ReissueService {
    ReissueResponse reissue(String refreshToken);
}