package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.dto.request.MissionCreateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.entity.Mission;
import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.MissionCreateService;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateServiceImpl implements MissionCreateService {

    private final MemberAuthService memberAuthService;
    private final MissionRepository missionRepository;
    private final MemberRepository membersRepository;

    @Override
    public MissionResponseDto createMission(MissionCreateRequestDto missionRequestDto, HttpServletRequest request) {
        log.debug("Create Mission: {}", missionRequestDto);
        Members fromMember = membersRepository.findByPhoneNumber(memberAuthService.getMemberByToken(request).getPhoneNumber());
        Members toWardMember = membersRepository.findByPhoneNumber(missionRequestDto.getToWard());
        log.debug("From Member: {}", fromMember);
        Mission mission = Mission.builder()
                .from(fromMember)
                .toWard(toWardMember)
                .status(Collections.singleton(Status.STATUS_PROGRESS))
                .startDate(LocalDateTime.now())
                .expirationDate(missionRequestDto.getExpirationDate())
                .title(missionRequestDto.getTitle())
                .content(missionRequestDto.getContent())
                .build();
        Objects.requireNonNull(fromMember).addMission(mission);
        membersRepository.save(fromMember);
        missionRepository.save(mission);
        return MissionResponseDto.from(mission);
    }
}