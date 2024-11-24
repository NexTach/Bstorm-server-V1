package com.nextech.server.v1.domain.mission.dto.request;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissionRequestDto {

    private Long from;
    private Long toWard;
    private Set<Status> status;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String title;
    private String content;
}
