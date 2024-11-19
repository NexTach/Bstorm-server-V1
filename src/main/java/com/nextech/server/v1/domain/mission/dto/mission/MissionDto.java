package com.nextech.server.v1.domain.mission.dto.mission;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MissionDto {

    private int id;
    private String from;
    private List<Status> Status;
    private String toWard;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String title;
    private String content;

}
