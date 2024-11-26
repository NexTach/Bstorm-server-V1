package com.nextech.server.v1.domain.mission.dto.request;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissionUpdateRequestDto {

    private Long toWard;
    private String title;
    private String content;
    private String expirationDate;
    private Set<Status> status;
}
