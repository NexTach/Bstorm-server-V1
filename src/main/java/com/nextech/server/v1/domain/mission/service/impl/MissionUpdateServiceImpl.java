package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.members.repository.MembersRepository;
import com.nextech.server.v1.domain.mission.dto.request.MissionUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.MissionUpdateService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MissionUpdateServiceImpl implements MissionUpdateService {

    private final MissionRepository missionRepository;
    private final MembersRepository membersRepository;

    @Override
    public MissionResponseDto updateMission(Long id, Members member, MissionUpdateRequestDto requestDto) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new LogNotFoundException("Mission not found"));

        Members toWardMember = membersRepository.findById(requestDto.getToWard())
                .orElseThrow(() -> new LogNotFoundException("ToWard member not found"));

        mission.setToWard(toWardMember);
        mission.setTitle(requestDto.getTitle());
        mission.setContent(requestDto.getContent());
        mission.setExpirationDate(LocalDateTime.parse(requestDto.getExpirationDate()));
        mission.setStatus(requestDto.getStatus());

        missionRepository.save(mission);

        return MissionResponseDto.from(mission);
    }
}
