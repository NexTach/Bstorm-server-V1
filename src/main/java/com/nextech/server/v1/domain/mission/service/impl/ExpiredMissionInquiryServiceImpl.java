package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.ExpiredMissionInquiryService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpiredMissionInquiryServiceImpl implements ExpiredMissionInquiryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionResponseDto> getExpiredMissions(Members member) {
        List<Status> statuses = new ArrayList<>(Collections.singleton(Status.STATUS_EXPIRED));
        List<Mission> missions = missionRepository.findByToWardIdAndStatusIn(member.getId(), statuses);

        if (missions.isEmpty()) {
            throw new LogNotFoundException("No expired missions found.");
        }

        return missions.stream()
                .map(MissionResponseDto::from)
                .collect(Collectors.toList());
    }
}
