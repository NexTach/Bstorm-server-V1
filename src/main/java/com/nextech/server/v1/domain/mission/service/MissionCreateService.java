package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.request.MissionCreateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MissionCreateService {
    MissionResponseDto createMission(MissionCreateRequestDto missionRequestDto, HttpServletRequest request);
}