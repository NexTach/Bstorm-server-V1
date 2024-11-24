package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;

import java.util.List;

public interface MissionService {

    MissionResponseDto createMission(MissionRequestDto missionRequestDto);
    List<MissionResponseDto> getAllMissions();
}
