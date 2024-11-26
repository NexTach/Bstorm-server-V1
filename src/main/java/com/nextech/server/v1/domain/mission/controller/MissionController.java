package com.nextech.server.v1.domain.mission.controller;

import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
import com.nextech.server.v1.domain.mission.dto.request.MissionStatusUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.request.MissionUpdateRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.service.*;
import com.nextech.server.v1.global.exception.ExpiredTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenFormatException;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final AllMissionInquiryService allMissionInquiryService;
    private final MissionCreateService missionCreateService;
    private final ParticularMissionInquiryService particularMissionInquiryService;
    private final UserMissionInquiryService userMissionInquiryService;
    private final MemberAuthService memberAuthService;
    private final SuccessfulMissionInquiryService successfulMissionService;
    private final ProgressMissionInquiryService progressMissionService;
    private final FailedMissionInquiryService failedMissionService;
    private final ExpiredMissionInquiryService expiredMissionService;
    private final SystemMissionInquiryService systemMissionService;
    private final CustomMissionInquiryService customMissionService;
    private final MissionUpdateService missionUpdateService;
    private final MissionStatusUpdateService missionStatusUpdateService;

    @GetMapping("/list")
    public ResponseEntity<List<MissionResponseDto>> getAllMissions() {
        List<MissionResponseDto> missions = allMissionInquiryService.getAllMissions();
        return ResponseEntity.ok(missions);
    }

    @PostMapping("/custom")
    public ResponseEntity<MissionResponseDto> createMission(@RequestBody MissionRequestDto missionRequestDto) {
        MissionResponseDto createdMission = missionCreateService.createMission(missionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionResponseDto> getMissionById(@PathVariable Long id) {
        MissionResponseDto mission = particularMissionInquiryService.getMissionById(id);
        return ResponseEntity.ok(mission);
    }

    @GetMapping
    public ResponseEntity<List<MissionResponseDto>> getUserMissions(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        List<MissionResponseDto> userMissions = userMissionInquiryService.getUserMissions(member);
        return ResponseEntity.ok(userMissions);
    }

    @GetMapping("/successful")
    public ResponseEntity<List<MissionResponseDto>> getSuccessfulMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> successfulMissions = successfulMissionService.getSuccessfulMissions(member);

            return ResponseEntity.ok(successfulMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/progress")
    public ResponseEntity<List<MissionResponseDto>> getProgressMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> progressMissions = progressMissionService.getProgressMissions(member);

            return ResponseEntity.ok(progressMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/failed")
    public ResponseEntity<List<MissionResponseDto>> getFailedMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> failedMissions = failedMissionService.getFailedMissions(member);

            return ResponseEntity.ok(failedMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/expired")
    private ResponseEntity<List<MissionResponseDto>> getExpiredMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> expiredMissions = expiredMissionService.getExpiredMissions(member);

            return ResponseEntity.ok(expiredMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/system")
    public ResponseEntity<List<MissionResponseDto>> getSystemMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> systemMissions = systemMissionService.getSystemMissions(member);

            return ResponseEntity.ok(systemMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/custom")
    public ResponseEntity<List<MissionResponseDto>> getCustomMissions(HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            List<MissionResponseDto> customMissions = customMissionService.getCustomMissions(member);

            if (customMissions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(customMissions);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROTECTOR', 'ROLE_DEVELOPER')")
    public ResponseEntity<MissionResponseDto> updateMission(
            @PathVariable Long id,
            @RequestBody MissionUpdateRequestDto requestDto,
            HttpServletRequest request) {
        try {
            Members member = memberAuthService.getMemberByToken(request);

            MissionResponseDto updatedMission = missionUpdateService.updateMission(id, member, requestDto);

            return ResponseEntity.ok(updatedMission);

        } catch (ExpiredTokenException | InvalidTokenException | InvalidTokenFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (LogNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROTECTOR', 'ROLE_DEVELOPER')")
    public ResponseEntity<MissionResponseDto> updateMissionStatus(
            @PathVariable Long id,
            @RequestBody MissionStatusUpdateRequestDto requestDto) {

        MissionResponseDto response = missionStatusUpdateService.updateMissionStatus(id, requestDto);
        return ResponseEntity.ok(response);
    }
}
