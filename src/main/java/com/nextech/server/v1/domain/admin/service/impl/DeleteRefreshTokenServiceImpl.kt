package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.dto.request.DeleteRefreshTokenRequest
import com.nextech.server.v1.domain.admin.service.DeleteRefreshTokenService
import com.nextech.server.v1.global.exception.RefreshTokenDeletionFailedException
import com.nextech.server.v1.global.exception.RefreshTokenNotFoundException
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber
import com.nextech.server.v1.global.redis.RedisUtil
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DeleteRefreshTokenServiceImpl(
    private val convertPhoneNumber: ConvertPhoneNumber,
    private val redisUtil: RedisUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) : DeleteRefreshTokenService {
    override fun deleteRefreshToken(toUser: DeleteRefreshTokenRequest) {
        val convertedPhoneNumber = convertPhoneNumber.convertPhoneNumber(toUser.toUsername)
        val refreshTokens: List<RefreshToken?> = refreshTokenRepository.findByUsername(convertedPhoneNumber)
        if (refreshTokens.isNotEmpty()) {
            refreshTokens.forEach { refreshToken ->
                refreshToken?.let { refreshTokenRepository.deleteByRefreshToken(it.refreshToken) }
                val redisKey = "refresh_token:${refreshToken?.refreshToken}"
                if (!redisUtil.delete(redisKey)) {
                    throw RefreshTokenDeletionFailedException("Failed to delete RefreshToken with key $redisKey from Redis")
                }
            }
        } else {
            throw RefreshTokenNotFoundException("Refresh Token Not Found for user: $convertedPhoneNumber")
        }
    }
}