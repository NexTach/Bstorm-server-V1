package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.SystemMissionInquiryService;
import com.nextech.server.v1.global.enums.Gender;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemMissionInquiryServiceImpl implements SystemMissionInquiryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionResponseDto> getSystemMissions(Members member) {
        Members systemMember = new Members(
                0L,
                "System",
                "+8200000000",
                "123456",
                1,
                Gender.MAN,
                Roles.ROLE_ADMIN,
                0,
                null,
                null,
                new ArrayList<>()
        );
        List<Mission> missions = missionRepository.findByFromAndToWard(systemMember, member);

        if (missions.isEmpty()) {
            throw new LogNotFoundException("No system-assigned missions found.");
        }

        return missions.stream()
                .map(MissionResponseDto::from)
                .collect(Collectors.toList());
    }
}
