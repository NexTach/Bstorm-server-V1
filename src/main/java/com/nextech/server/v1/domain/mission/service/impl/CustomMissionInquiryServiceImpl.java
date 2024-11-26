package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.CustomMissionInquiryService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomMissionInquiryServiceImpl implements CustomMissionInquiryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionResponseDto> getCustomMissions(Members member) {
        List<Mission> missions = missionRepository.findByFrom(member.getId());

        if (missions.isEmpty()) {
            throw new LogNotFoundException("No custom missions found.");
        }

        return missions.stream()
                .map(MissionResponseDto::from)
                .collect(Collectors.toList());
    }
}

