package com.nextech.server.v1.domain.mission.dto.request

import java.time.LocalDateTime

data class MissionCreateRequestDto(
    val toWard: String,
    val title: String,
    val content: String,
    val expirationDate: LocalDateTime
)