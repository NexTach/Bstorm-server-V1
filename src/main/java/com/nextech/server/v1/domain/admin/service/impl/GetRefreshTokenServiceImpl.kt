package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.service.GetRefreshTokenService
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class GetRefreshTokenServiceImpl(private val refreshTokenRepository: RefreshTokenRepository) : GetRefreshTokenService {
    override fun getRefreshToken(): List<RefreshToken> {
        return refreshTokenRepository.findAll().toList()
    }
}