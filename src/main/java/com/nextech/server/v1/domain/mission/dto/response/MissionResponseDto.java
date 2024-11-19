package com.nextech.server.v1.domain.mission.dto.response;

import com.nextech.server.v1.domain.mission.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MissionResponseDto {

    private List<Mission> body;
}
