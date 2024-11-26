package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.members.repository.MembersRepository;
import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.service.MissionCreateService;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateServiceImpl implements MissionCreateService {

    private final MissionRepository missionRepository;
    private final MembersRepository membersRepository;

    @Override
    public MissionResponseDto createMission(MissionRequestDto missionRequestDto) {
        Members fromMember = membersRepository.findById(missionRequestDto.getFrom())
                .orElseThrow(() -> new LogNotFoundException("From member not found"));
        Members toWardMember = membersRepository.findById(missionRequestDto.getToWard())
                .orElseThrow(() -> new LogNotFoundException("To ward member not found"));

        Mission mission = Mission.builder()
                .from(fromMember)
                .toWard(toWardMember)
                .status(missionRequestDto.getStatus())
                .startDate(missionRequestDto.getStartDate())
                .expirationDate(missionRequestDto.getExpirationDate())
                .title(missionRequestDto.getTitle())
                .content(missionRequestDto.getContent())
                .build();

        Mission savedMission = missionRepository.save(mission);
        return MissionResponseDto.from(savedMission);
    }
}
