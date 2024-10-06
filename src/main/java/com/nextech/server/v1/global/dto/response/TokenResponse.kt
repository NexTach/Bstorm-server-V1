package com.nextech.server.v1.global.dto.response

import com.nextech.server.v1.global.enums.Roles
import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresI: LocalDateTime,
    val refreshTokenExpiresIn: LocalDateTime,
    val roles: Roles
)