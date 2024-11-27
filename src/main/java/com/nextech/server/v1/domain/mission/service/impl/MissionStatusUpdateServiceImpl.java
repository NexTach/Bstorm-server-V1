package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.request.MissionStatusUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.entity.MissionList;
import com.nextech.server.v1.domain.mission.repository.MissionListRepository;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.MissionStatusUpdateService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionStatusUpdateServiceImpl implements MissionStatusUpdateService {

    private final MissionRepository missionRepository;
    private final MissionListRepository missionListRepository;
    private final MemberAuthService memberAuthService;

    @Override
    public MissionResponseDto updateMissionStatus(Long id, MissionStatusUpdateRequestDto requestDto, HttpServletRequest request) {
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

        Members member = memberAuthService.getMemberByToken(request);
        List<MissionList> missionList = missionListRepository.findByMember(member);
        missionList.forEach(missionList1 -> {
            if (missionList1.getDate().equals(LocalDate.now())) {
                int completions = missionList1.getCompletions();
                completions++;
                missionList1.setCompletions(completions);
            }
        });
        missionListRepository.saveAll(missionList);
        return MissionResponseDto.from(mission);
    }
}