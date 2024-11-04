package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.service.AllDeleteRefreshTokenService
import com.nextech.server.v1.global.exception.RefreshTokenNotFoundException
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class AllDeleteRefreshTokenServiceImpl(private val refreshTokenRepository: RefreshTokenRepository) :
    AllDeleteRefreshTokenService {
    override fun allDeleteRefreshToken() {
        if (refreshTokenRepository.count() > 0) {
            refreshTokenRepository.deleteAll()
        } else {
            throw RefreshTokenNotFoundException("No refresh token found")
        }
    }
}