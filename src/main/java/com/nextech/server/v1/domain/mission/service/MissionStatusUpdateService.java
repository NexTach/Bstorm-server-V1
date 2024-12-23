package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.request.MissionStatusUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MissionStatusUpdateService {
    MissionResponseDto updateMissionStatus(Long id, MissionStatusUpdateRequestDto requestDto, HttpServletRequest request);
}
