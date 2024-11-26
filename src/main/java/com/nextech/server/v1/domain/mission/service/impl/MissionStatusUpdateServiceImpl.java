package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.request.MissionStatusUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.MissionStatusUpdateService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MissionStatusUpdateServiceImpl implements MissionStatusUpdateService {

    private final MissionRepository missionRepository;

    @Override
    public MissionResponseDto updateMissionStatus(Long id, MissionStatusUpdateRequestDto requestDto) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new LogNotFoundException("Mission not found"));

        LocalDateTime expirationDate = LocalDateTime.parse(requestDto.getExpirationDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration date cannot be in the past");
        }

        if (mission.getStatus().contains(requestDto.getStatus())) {
            throw new IllegalStateException("Mission is already in the specified status");
        }

        mission.setExpirationDate(expirationDate);
        mission.setStatus(requestDto.getStatus());

        missionRepository.save(mission);

        return MissionResponseDto.from(mission);
    }
}
