package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;

public interface ParticularMissionInquiryService {
    MissionResponseDto getMissionById(Long id);
}
