package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.global.members.entity.Members;

import java.util.List;

public interface ExpiredMissionInquiryService {
    List<MissionResponseDto> getExpiredMissions(Members member);
}