package com.nextech.server.v1.domain.mission.dto.request;

import com.nextech.server.v1.domain.mission.dto.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MissionStatusUpdateRequestDto {
    private String expirationDate;
    private Set<Status> status;
}
