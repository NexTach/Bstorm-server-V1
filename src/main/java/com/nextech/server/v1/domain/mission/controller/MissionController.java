package com.nextech.server.v1.domain.mission.controller;

import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
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
    private final SuccessfulMissionService successfulMissionService;

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
}
