package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;

import java.util.List;

public interface AllMissionInquiryService {
    List<MissionResponseDto> getAllMissions();
}
