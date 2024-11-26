package com.nextech.server.v1.domain.mission.service.impl;

import com.nextech.server.v1.domain.mission.repository.MissionRepository;
import com.nextech.server.v1.domain.mission.service.MissionDeleteService;
import com.nextech.server.v1.global.exception.LogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionDeleteServiceImpl implements MissionDeleteService {

    private final MissionRepository missionRepository;

    @Override
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new LogNotFoundException("Mission not found");
        }
        missionRepository.deleteById(id);
    }
}
