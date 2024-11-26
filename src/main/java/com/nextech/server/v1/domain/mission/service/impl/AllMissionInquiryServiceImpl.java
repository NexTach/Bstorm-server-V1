package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.AllMissionInquiryService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllMissionInquiryServiceImpl implements AllMissionInquiryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionResponseDto> getAllMissions() {
        List<Mission> missions = missionRepository.findAll();
        if (missions.isEmpty()) {
            throw new LogNotFoundException("No missions found");
        }

        return missions.stream()
                .map(MissionResponseDto::from)
                .collect(Collectors.toList());
    }
}
