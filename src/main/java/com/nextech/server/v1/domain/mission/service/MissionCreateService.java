package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;

public interface MissionCreateService {
    MissionResponseDto createMission(MissionRequestDto missionRequestDto);
}
