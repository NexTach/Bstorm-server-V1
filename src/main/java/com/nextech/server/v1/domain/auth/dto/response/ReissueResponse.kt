package com.nextech.server.v1.domain.auth.dto.response

import com.nextech.server.v1.global.enums.Roles
import java.time.LocalDateTime

data class ReissueResponse(
    val accessToken: String,
    val accessTokenExpiresIn: LocalDateTime,
    val role: Roles
)