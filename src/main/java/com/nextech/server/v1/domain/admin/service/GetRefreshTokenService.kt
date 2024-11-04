package com.nextech.server.v1.domain.admin.service

import com.nextech.server.v1.global.security.jwt.entity.RefreshToken

interface GetRefreshTokenService {
    fun getRefreshToken(): List<RefreshToken>
}