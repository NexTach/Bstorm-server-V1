package com.nextech.server.v1.global.security.jwt.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash("refresh_token", timeToLive = 604800)
data class RefreshToken(
    @Id val refreshToken: String, @Indexed val username: String, val expiredAt: LocalDateTime
)