package com.nextech.server.v1.domain.mission.dto.response;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.domain.mission.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionResponseDto {

    private Long id;
    private String from;
    private String toWard;
    private Set<Status> status;
    private String startDate;
    private String expirationDate;
    private String title;
    private String content;

    public static MissionResponseDto from(Mission mission) {
        return MissionResponseDto.builder()
                .id(mission.getId())
                .from(mission.getFrom().getName())
                .toWard(mission.getToWard().getName())
                .status(mission.getStatus())
                .startDate(mission.getStartDate().toString())
                .expirationDate(mission.getExpirationDate().toString())
                .title(mission.getTitle())
                .content(mission.getContent())
                .build();
    }
}
