package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.ParticularMissionInquiryService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticularMissionInquiryServiceImpl implements ParticularMissionInquiryService {

    private final MissionRepository missionRepository;

    @Override
    public MissionResponseDto getMissionById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new LogNotFoundException("Mission not found with id: " + id));

        return MissionResponseDto.from(mission);
    }
}
