package com.nextech.server.v1.domain.mission.controller;

import com.nextech.server.v1.domain.mission.dto.request.MissionRequestDto;
import com.nextech.server.v1.domain.mission.dto.response.MissionResponseDto;
import com.nextech.server.v1.domain.mission.service.MissionService;
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

    private final MissionService missionService;

    @GetMapping("/list")
    public ResponseEntity<List<MissionResponseDto>> getAllMissions() {
        List<MissionResponseDto> missions = missionService.getAllMissions();
        return ResponseEntity.ok(missions);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROTECTOR', 'ROLE_DEVELOPER')")
    @PostMapping("/custom")
    public ResponseEntity<MissionResponseDto> createMission(@RequestBody MissionRequestDto missionRequestDto) {
        MissionResponseDto createdMission = missionService.createMission(missionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMission);
    }
}
