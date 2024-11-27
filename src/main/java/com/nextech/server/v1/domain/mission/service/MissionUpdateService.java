package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.request.MissionUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.global.members.entity.Members;

public interface MissionUpdateService {
    MissionResponseDto updateMission(Long id, Members member, MissionUpdateRequestDto requestDto);
}
