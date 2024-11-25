package com.nextech.server.v1.domain.mission.service;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.global.members.entity.Members;

import java.util.List;

public interface FailedMissionInquiryService {
    List<MissionResponseDto> getFailedMissions(Members member);
}
