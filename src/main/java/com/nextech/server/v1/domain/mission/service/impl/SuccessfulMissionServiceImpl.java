package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.SuccessfulMissionService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuccessfulMissionServiceImpl implements SuccessfulMissionService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionResponseDto> getSuccessfulMissions(Members member) {
        List<Mission> missions = missionRepository.findByToWardAndStatus(member, Status.STATUS_SUCCESSFUL);

        if (missions.isEmpty()) {
            throw new LogNotFoundException("No successful missions found");
        }

        return missions.stream()
                .map(MissionResponseDto::from)
                .collect(Collectors.toList());
    }
}
